package com.simon.tools.oracle;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class OracleBeans {
    private static final Logger logger = LoggerFactory.getLogger(BatchUpdate.class);
    private @Value("${db.user}") String user;
    private @Value("${db.password}") String password;
    private @Value("${db.host}") String host;
    private @Value("${db.port}") String port;
    private @Value("${db.sid}") String serviceName;
    private @Value("${db.init.pool.size}") int initialPoolSize;
    private @Value("${db.min.pool.size}") int minPoolSize;
    private @Value("${db.max.pool.size}") int maxPoolSize;

    @Bean
    public PoolDataSource getDataSource() {
        PoolDataSource dataSource = PoolDataSourceFactory.getPoolDataSource();
        try {
            dataSource.setUser(user);
            dataSource.setPassword(password);
            dataSource.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
            dataSource.setURL(String.format("jdbc:oracle:thin:@//%s:%s/%s",host,port,serviceName));
            dataSource.setFastConnectionFailoverEnabled(true);
            dataSource.setInitialPoolSize(initialPoolSize);
            dataSource.setMinPoolSize(minPoolSize);
            dataSource.setMaxPoolSize(maxPoolSize);
        } catch (SQLException e) {
            logger.error("failed to create been for data source", e);
            return null;
        }
        return dataSource;
    }

    @Bean()
    public ExecutorService getFixedThreadPool() {
        return Executors.newFixedThreadPool(maxPoolSize);
    }
}
