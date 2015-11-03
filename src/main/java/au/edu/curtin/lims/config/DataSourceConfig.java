/*
 * Copyright Curtin University, 2015.
 */
package au.edu.curtin.lims.config;

import au.edu.curtin.lims.controller.BaseController;

import org.postgresql.ds.PGSimpleDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);
    
    @Bean
    public PGSimpleDataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUser("curtin_lims_user");
        dataSource.setDatabaseName("curtin_lims");
        dataSource.setPortNumber(5432);
        
        // TODO: This should be changed to use JNDI but I had problems with it translating geospatial objects when I tried to set it up...
        dataSource.setPassword(BaseController.databasePassword);
        dataSource.setServerName(BaseController.databaseHostname);
       
        LOGGER.info("Datasource specified: postgres://{}:{password-suppressed}@{}:{}/{}", 
                dataSource.getUser(),
                dataSource.getServerName(),
                dataSource.getDatabaseName(),
                dataSource.getPortNumber());
        
        return dataSource;
        
//        JndiObjectFactoryBean jndiObjectFB = new JndiObjectFactoryBean();
//        jndiObjectFB.setJndiName("jdbc/curtin_lims");
//        jndiObjectFB.setResourceRef(true);
//        jndiObjectFB.setProxyInterface(javax.sql.DataSource.class);
//        return jndiObjectFB;
    }
}
