package com.retailio.backendchallenge.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

/**
 * @author parag
 *
 *         Service for properly identifying and retrieving the enviorment
 *         properties.
 */
@Service("ConfigUtils")
public class ConfigUtils {

	public Properties properties = new Properties();

	private Logger logger = LogFactory.getLogger(ConfigUtils.class);

	public ConfigUtils() {
		loadCommonProperties();
		//loadEnvProperties();
		logger.info("Config Utils started");
	}

	private void loadCommonProperties() {
		// String confDir = "classpath:" + applicationPropertiesFilePath;

		try {
			Resource resource = new ClassPathResource("/application.properties");
			this.properties = PropertiesLoaderUtils.loadProperties(resource);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	private void loadEnvProperties() {
		InputStream is = null;
		Properties properties = new Properties();
		String confDir = "ENV-" + this.properties.getProperty("environment");
		try {
			final String appenginePropertiesFilePath = confDir
					+ java.io.File.separator + "application.properties";
			is = ConfigUtils.class.getClassLoader().getResourceAsStream(
					appenginePropertiesFilePath);
			properties.load(is);
			for (Entry<Object, Object> entry : properties.entrySet()) {
				this.properties.put(entry.getKey(), entry.getValue());
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new RuntimeException(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {

				}
			}
			properties.clear();
		}

	}

	public String getStringValue(String key) {

		String value = this.properties.getProperty(key);
		return value == null ? value : value.trim();
	}

	public int getIntValue(String key) {

		return Integer.parseInt(properties.getProperty(key).trim());
	}

	public long getLongValue(String key) {

		return Long.parseLong(properties.getProperty(key).trim());
	}

	public boolean getBooleanValue(String key) {

		return properties.getProperty(key) == null ? false : Boolean
				.parseBoolean(properties.getProperty(key).trim());
	}

	public float getFloatValue(String key) {

		return Float.parseFloat(properties.getProperty(key).trim());
	}
}
