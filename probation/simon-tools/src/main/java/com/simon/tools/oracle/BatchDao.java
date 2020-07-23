package com.simon.tools.oracle;

import oracle.ucp.jdbc.PoolDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BatchDao {
    private static final Logger logger = LoggerFactory.getLogger(BatchUpdate.class);

    private @Autowired ExecutorService executorService;
    private @Autowired PoolDataSource dataSource;

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
                try (Connection conn = dataSource.getConnection()) {
                    while (!stop.get() || sqlQueue.size() > 0) {
                        String sql = sqlQueue.poll();
                        try (PreparedStatement statement = conn.prepareStatement(sql)) {
                            statement.execute();
                            int rowNumber = rowCount.incrementAndGet();
                            logger.info(rowNumber + ":" + sql);
                        }
                    }
                } catch (SQLException e) {
                    logger.error("failed to get connection", e);
                }
            });
    }

    public void stop() {
        stop.set(true);
        executorService.shutdown();
    }
}
