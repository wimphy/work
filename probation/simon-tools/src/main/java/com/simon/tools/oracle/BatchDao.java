package com.simon.tools.oracle;

import com.simon.tools.services.BatchUpdate;
import oracle.ucp.jdbc.PoolDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static com.simon.tools.utils.Common.close;

//@Service
//@DependsOn("sqlHistory")
public class BatchDao {
    private static final Logger logger = LoggerFactory.getLogger(BatchUpdate.class);

    private @Autowired ExecutorService executorService;
    private @Autowired PoolDataSource dataSource;
    //private @Autowired SQLHistory sqlHistory;

    private @Value("${db.rows.batch}") int batchSize;
    private @Value("${db.max.pool.size}") int threadNumber;

    private ArrayBlockingQueue<String> sqlQueue = new ArrayBlockingQueue<>(2000);
    private AtomicBoolean stop = new AtomicBoolean(false);
    private AtomicInteger rowCount = new AtomicInteger(0);


    public void addTask(String sql) throws InterruptedException {
        if (sql == null) {
            return;
        }
        sqlQueue.put(sql);
    }

    @PostConstruct
    public void doUpdate() {
        for (int i = 0; i < threadNumber; i++)
            executorService.execute(() -> {
                try {
                    int count = dataSource.getAvailableConnectionsCount();
                    if (count == 0) {
                        logger.info("no available connection count, waiting");
                    }
                } catch (SQLException e) {
                    //do nothing
                }
                Connection conn = null;
                try {
                    conn = dataSource.getConnection();
                    while (!stop.get() || sqlQueue.size() > 0) {
                        String sql = sqlQueue.poll();
//                        if (sqlHistory.isSaved(sql)) {
//                            logger.info("polled saved sql, do nothing, continue");
//                            continue;
//                        }
                        logger.info("polled valid sql");
                        conn = doTask(conn, sql);
                        int rowNumber = rowCount.incrementAndGet();
                        logger.info(rowNumber + ":" + sql);
                        //sqlHistory.save(sql);
                    }
                } catch (SQLException e) {
                    logger.error("failed to update", e);
                } finally {
                    close(conn);
                }
            });
    }

    protected Connection doTask(Connection conn, String sql) {
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.execute();
        } catch (Exception e) {
            logger.info("SQL failed retrying ...", e);
            conn = retry(conn, sql);
        }
        return conn;
    }

    private Connection retry(Connection oldConnection, String sql) {
        close(oldConnection);
        long repeat = 0;
        Connection conn;
        PreparedStatement statement = null;
        while (true)
            try {
                conn = dataSource.getConnection();
                statement = conn.prepareStatement(sql);
                statement.execute();
                break;
            } catch (SQLException e) {
                logger.error("sql failed", e);
                repeat++;
                try {
                    Thread.sleep(repeat * repeat * 1000);
                } catch (InterruptedException ex) {
                    //do nothing
                }
            } finally {
                close(statement);
            }
        return conn;
    }

    public void stop() {
        stop.set(true);
        executorService.shutdown();
    }
}
