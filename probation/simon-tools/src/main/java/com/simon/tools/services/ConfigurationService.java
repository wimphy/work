package com.simon.tools.services;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.sql.*;
import java.util.Properties;

@Service
public class ConfigurationService {
    private static final Logger logger = LoggerFactory.getLogger(BatchUpdate.class);
    private static final String lastUpdateSQL = "select timestamp from USER_TAB_MODIFICATIONS where table_name = 'CONFIGURATION'";
    private static final String keyValueSQL = "select * from configuration";

    private @Value("${config.cache.location}") String cacheLocation;
    private @Value("${config.db.user}") String user;
    private @Value("${config.db.password}") String password;
    private @Value("${config.db.host}") String host;
    private @Value("${config.db.port}") String port;
    private @Value("${config.db.sid}") String serviceName;

    Properties properties = new Properties();

    @PostConstruct
    private void load() {
        try (Connection conn = getConnection()) {
            Timestamp db = getLastUpdateFromDB(conn);
            Timestamp cache = getUpdateDateFromCache();
            if (db != null && cache != null && db.after(cache) || db != null && cache == null) {
                refreshCache(conn);
            }
            loadFromCache();
        } catch (SQLException e) {
            logger.error("configuration db error", e);
        } catch (IOException ioe) {
            logger.error("configuration cache error", ioe);
        }
    }

    private Connection getConnection() {
        Connection conn;
        PoolDataSource dataSource = PoolDataSourceFactory.getPoolDataSource();
        try {
            dataSource.setUser(user);
            dataSource.setPassword(password);
            dataSource.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
            dataSource.setURL(String.format("jdbc:oracle:thin:@//%s:%s/%s", host, port, serviceName));
            dataSource.setFastConnectionFailoverEnabled(true);
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            logger.error("failed to create conn for data source", e);
            return null;
        }
        return conn;
    }

    private Timestamp getLastUpdateFromDB(Connection conn) throws SQLException {
        if (conn == null) {
            return null;
        }
        try (PreparedStatement statement = conn.prepareStatement(lastUpdateSQL)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getTimestamp(1);
        }
    }

    private Timestamp getUpdateDateFromCache() {
        return null;
    }

    private void refreshCache(Connection conn) throws SQLException, IOException {
        try (PreparedStatement statement = conn.prepareStatement(keyValueSQL);
             BufferedWriter writer = new BufferedWriter(new FileWriter(cacheLocation))) {
            writer.write("#This is automatically generated properties file, please do not modify");
            writer.newLine();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String key = resultSet.getString(2);
                String value = resultSet.getString(3);
                writer.append(key).append("=").append(value);
                writer.newLine();
            }
        }
    }

    private void loadFromCache() throws IOException {
        try (FileReader reader = new FileReader(cacheLocation)) {
            properties.load(reader);
        }
    }

    public String get(String key) {
        return (String) properties.get(key);
    }
}
