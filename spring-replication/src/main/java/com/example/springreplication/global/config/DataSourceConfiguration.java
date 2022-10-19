package com.example.springreplication.global.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Configuration
public class DataSourceConfiguration {

    private static final String MASTER_SERVER = "MASTER";
    private static final String SLAVE_SERVER = "SLAVE";

    @Bean
    @Qualifier(MASTER_SERVER)
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create()
            .build();
    }

    @Bean
    @Qualifier(SLAVE_SERVER)
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create()
            .build();
    }

    @Bean
    public DataSource routingDataSource(@Qualifier(MASTER_SERVER) DataSource masterDataSource,
                                        @Qualifier(SLAVE_SERVER) DataSource slaveDataSource
    ) {
        RoutingDataSource routingDataSource = new RoutingDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(MASTER_SERVER, masterDataSource);
        dataSourceMap.put(SLAVE_SERVER, slaveDataSource);

        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);

        return routingDataSource;
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        DataSource determinedDataSource = routingDataSource(masterDataSource(), slaveDataSource());
        return new LazyConnectionDataSourceProxy(determinedDataSource);
    }

    static class RoutingDataSource extends AbstractRoutingDataSource {

        private static final Logger LOGGER = LoggerFactory.getLogger(RoutingDataSource.class);

        @Override
        protected Object determineCurrentLookupKey() {
            boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
            LOGGER.info("Transaction의 Read Only가 {} 입니다.", isReadOnly);

            if (isReadOnly) {
                LOGGER.info("Slave 서버로 요청");
                return SLAVE_SERVER;
            }

            LOGGER.info("Master 서버로 요청");
            return MASTER_SERVER;
        }
    }
}
