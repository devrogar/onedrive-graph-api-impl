package com.devrogar.utility.config;

import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.devrogar.utility.enums.PropertyKey;

/**
 * Loads initial boot and other required properties
 * <hr>
 * Rename the template present in the resources folder
 * <hr>
 * <br>
 * @author Rohit Garlapati
 *
 */
@Component
public class SystemProperties {
	
	private static final Logger logger = LoggerFactory.getLogger(SystemProperties.class);
	
	protected final Properties properties = new Properties();
	
	@PostConstruct
	private void loadProperties() {
		loadProperties("application.properties");
	}
	
	protected void loadProperties(String propertiesFileName) {
		try (InputStream is = new ClassPathResource(propertiesFileName).getInputStream()) {
			logger.info("Loading properties {}", propertiesFileName);
			properties.load(is);
			logger.info("Loading properties {} successful", propertiesFileName);
		} catch (Exception e) {
			logger.error("Error occurred while loading properties, {}", e.getMessage());
			System.exit(1);
		}
	}

	public String get(PropertyKey propertyKey) {
		return (String) properties.get(propertyKey.key());
	}

}
