package com.simon.tools.oracle.rollout;

import com.simon.tools.GenWriter;
import com.simon.tools.services.GenTask;
import com.simon.tools.utils.Common;
import com.simon.tools.utils.Strings;
import oracle.ucp.jdbc.PoolDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RollBackGenerator extends UpdateGenerator {
    private static final Logger logger = LoggerFactory.getLogger(RollBackGenerator.class);
    //Oracle allows up to 1,000 IN list values in a SQL statement.
    private static final int maxIdCount = 999;
    private static final String sqlFormat = "select id, description from %s where id in (%s)";

    private @Autowired PoolDataSource dataSource;
    private Connection connection = null;
    private Map<String, ArrayList<GenTask>> ids = new ConcurrentHashMap<>();

    private void doTask(int size) {
        for (String table : ids.keySet()) {
            ArrayList<GenTask> tasks = ids.get(table);
            if (tasks.size() < size) {
                continue;
            }
            connection = getConnection();
            if (connection == null) {
                continue;
            }
            ArrayList<String> idList = new ArrayList<>();
            for (GenTask task : tasks) {
                idList.add(task.getId());
            }
            String sql = String.format(sqlFormat, table, String.join(",", idList));
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String id = resultSet.getString(1);
                    String description = resultSet.getString(2);
                    description = Strings.toSQLString(description);
                    GenTask old = tasks.stream()
                            .filter(task -> id.equals(task.getId()))
                            .findAny()
                            .orElse(null);
                    if (old != null) {
                        old.setDescription(description);
                    }
                }
            } catch (SQLException e) {
                logger.error("db error", e);
            }
            for (GenTask task : tasks) {
                super.doTask(task);
            }
            ids.remove(table);
        }
    }

    @Override
    protected void doTask(GenTask genTask) {
        ArrayList<GenTask> list = ids.computeIfAbsent(genTask.getTableName(), k -> new ArrayList<>());
        list.add(genTask);
        doTask(maxIdCount);
    }

    @Override
    protected GenWriter createNewWriter(GenTask genTask, String index) throws IOException {
        return super.createNewWriter(genTask, "rollback");
    }

    @Override
    protected void cleanup() {
        doTask(0);//for the remaining tasks
        super.cleanup();
        Common.close(connection);
    }

    private Connection getConnection() {
        try {
            if (connection == null)
                connection = dataSource.getConnection();
        } catch (SQLException e) {
            logger.error("failed to get connection", e);
            return null;
        }
        return connection;
    }
}
