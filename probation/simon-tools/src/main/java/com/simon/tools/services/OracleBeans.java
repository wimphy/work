package com.simon.tools.services;

import com.simon.tools.oracle.SQLHistory;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@DependsOn("configurationService")
public class OracleBeans {
    private static final Logger logger = LoggerFactory.getLogger(BatchUpdate.class);
    private @Value("${config.db.user}") String user;
    private @Value("${config.db.password}") String password;
    private @Value("${config.db.host}") String host;
    private @Value("${config.db.port}") String port;
    private @Value("${config.db.sid}") String serviceName;

    private @Value("${db.env}") String env;
    private @Value("${db.init.pool.size}") int initialPoolSize;
    private @Value("${db.min.pool.size}") int minPoolSize;
    private @Value("${db.max.pool.size}") int maxPoolSize;
    private @Value("${log.history.name}") String fileName;

    @Bean
    public PoolDataSource getDataSource(@Autowired ConfigurationService configurationService) {
        PoolDataSource dataSource = PoolDataSourceFactory.getPoolDataSource();
        try {
            dataSource.setUser(configurationService.get(env + ".db.user"));
            dataSource.setPassword(configurationService.get(env + ".db.password"));
            dataSource.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
            dataSource.setURL(String.format("jdbc:oracle:thin:@//%s:%s/%s",
                    configurationService.get(env + ".db.host"),
                    configurationService.get(env + ".db.port"),
                    configurationService.get(env + ".db.sid")));
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

    @Bean("sqlHistory")
    public SQLHistory getSQLHistory() {
        return new SQLHistory(fileName);
    }
}
