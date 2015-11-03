/*
 * Copyright Curtin University, 2015.
 */
package au.edu.curtin.lims.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.text.MessageFormat;
import java.util.Properties;

import javax.servlet.ServletContext;

import au.edu.curtin.lims.controller.BaseController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.multipart.support.MultipartFilter;

public class SecurityWebInitializer extends AbstractSecurityWebApplicationInitializer {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityWebInitializer.class);
    
    private static final String OS_NAME = System.getProperty("os.name");
    
    private String readProperty(Properties properties, String hostname, String propertyName) {
        String key = hostname + "." + propertyName;
        String propertyValue = properties.getProperty(key);
        
        // If we can't find a hostname-specific value then we'll just use the OS default instead:
        if (propertyValue == null) {
            key = OS_NAME + "_" + propertyName;
            propertyValue = properties.getProperty(key);
            
            if (propertyValue == null) {
                String errorMessage = MessageFormat.format("No config.properties entry for {0}.{1} or {2}", hostname, propertyName, key);
                LOGGER.error(errorMessage);
                throw new RuntimeException(errorMessage);
            }
            else {
                LOGGER.warn("No config.properties entry for {}.{}. Using {} instead", hostname, propertyName, key);
            }
        }
        
        LOGGER.info("config.properties: {}={}", key, propertyValue);
        return propertyValue;
    }
    
    @Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
        insertFilters(servletContext, new MultipartFilter());

        // TODO: This is where I'm setting properties. It should be moved somewhere else for separation of concerns.
        Properties properties = new Properties();
        InputStream input = null;
        try {
            String hostname = InetAddress.getLocalHost().getHostName();
            input = servletContext.getResourceAsStream("/WEB-INF/resources/config.properties");            
            properties.load(input);

            LOGGER.info("Reading config.properties with hostname prefix {}", hostname);
            
            String configPath = this.readProperty(properties, hostname, "configPath");
            properties.clear();
            properties.load(new FileInputStream(configPath));
            
            BaseController.stagingPath = this.readProperty(properties, hostname, "stagingPath");
            BaseController.scriptsPath = this.readProperty(properties, hostname, "scriptsPath");
            BaseController.redboxPath = this.readProperty(properties, hostname, "redboxPath");
            BaseController.ddfePath = this.readProperty(properties, hostname, "ddfePath");
            BaseController.databasePassword = this.readProperty(properties, hostname, "databasePassword");
            BaseController.databaseHostname = this.readProperty(properties, hostname, "databaseHostname");
            BaseController.sesarUsername = this.readProperty(properties, hostname, "sesarUsername");
            BaseController.sesarPassword = this.readProperty(properties, hostname, "sesarPassword");
            BaseController.sesarUserCode = this.readProperty(properties, hostname, "sesarUserCode");
            BaseController.databaseConnectionString = "postgres://curtin_lims_user:" + BaseController.databasePassword + "@" + BaseController.databaseHostname + "/curtin_lims";
            BaseController.sesarCsvImportPath = servletContext.getRealPath("/WEB-INF/classes/sesar_csv_import.py");

            // TODO: make sure this path exists: BaseController.sesarCsvImportPath. If not, suggest expand WAR.
            LOGGER.info("Set sesarCsvImportPath to {}", BaseController.sesarCsvImportPath);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
