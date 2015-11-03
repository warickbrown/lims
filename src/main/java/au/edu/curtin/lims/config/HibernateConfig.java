/*
 * Copyright Curtin University, 2015.
 */
package au.edu.curtin.lims.config;

import java.util.Enumeration;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

@Configuration
public class HibernateConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateConfig.class);
    
    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
        sfb.setDataSource(dataSource);
        sfb.setPackagesToScan(new String[] {"au.edu.curtin.lims.domain"});
        
        // See list here: https://docs.jboss.org/hibernate/orm/4.1/manual/en-US/html/ch03.html
        Properties props = new Properties();
        props.setProperty("hibernate.dialect", "org.hibernate.spatial.dialect.postgis.PostgisDialect");
        props.setProperty("hibernate.show_sql", "true");
        props.setProperty("hibernate.format_sql", "true");
        //props.setProperty("hibernate.default_schema", ???);
        //props.setProperty("hibernate.default_catalog", ???);
        //props.setProperty("hibernate.session_factory_name", ???);
        //props.setProperty("hibernate.max_fetch_depth", ???);
        //props.setProperty("hibernate.default_batch_fetch_size", ???);
        props.setProperty("hibernate.default_entity_mode", "pojo");
        //props.setProperty("hibernate.order_updates", ???);
        //props.setProperty("hibernate.generate_statistics", ???);
        //props.setProperty("hibernate.use_identifier_rollback", ???);
        props.setProperty("hibernate.use_sql_comments", "true");        
        props.setProperty("hibernate.id.new_generator_mappings", "true"); // SEE: https://docs.jboss.org/hibernate/orm/4.1/manual/en-US/html/ch03.html search: "We recommend all new projects which make use of to use"
        
        sfb.setHibernateProperties(props);
        
        if (LOGGER.isInfoEnabled()) {
            Enumeration<?> propertyNames = props.propertyNames(); 
            while (propertyNames.hasMoreElements()) {
                String propertyName = propertyNames.nextElement().toString();
                LOGGER.info("Session factory bean property set: {}={}", propertyName, props.getProperty(propertyName));
            }
        }
        
        return sfb;
    }    

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }
}
