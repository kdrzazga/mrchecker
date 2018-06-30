package com.capgemini.mrchecker.database.core.base.properties;

import com.capgemini.mrchecker.test.core.logger.BFLogger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {

	private static String appConfigPath = "C:\\Users\\Przemek\\Documents\\Projects\\devonfw-testing\\mrchecker-framework-modules\\mrchecker-database-module\\src\\test\\resources\\settings.properties";

	private static final String jpaProviderPropertyName = "database.jpa_provider";
	private static final String usernamePropertyName    = "database.username";
	private static final String passwordPropertyName    = "database.password";
	private static final String hostnamePropertyName    = "database.hostname";
	private static final String portPropertyName        = "database.port";
	private static final String jdbcDriverPropertyName  = "database.jdbc_driver_class";
	private static final String showSql                 = "database.show_sql";

	public static Properties readProperties() {
		Properties jpaProperties = new Properties();
		Properties applicationProperties = new Properties();
		try {
			applicationProperties.load(new FileInputStream(appConfigPath));
			jpaProperties = mapProperties(applicationProperties);
		} catch (IOException e) {
			BFLogger.logError("Unable to load properties file from path: [" + appConfigPath + "]");
		}
		return jpaProperties;
	}

	private static Properties mapProperties(Properties applicationProperties) {
		Properties jpaProperties = new Properties();

		String provider = applicationProperties.getProperty(jpaProviderPropertyName, "org.hibernate.jpa.HibernatePersistenceProvider");
		switch (provider) {
			case "org.hibernate.jpa.HibernatePersistenceProvider":
				jpaProperties = applyHibernateProperties(applicationProperties);
		}
		return jpaProperties;
	}

	private static Properties applyHibernateProperties(Properties jpaProperties) {

		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.connection.url", jpaProperties.getProperty(hostnamePropertyName).concat(":").concat(jpaProperties.getProperty(portPropertyName)));
		hibernateProperties.put("hibernate.connection.username", jpaProperties.getProperty(usernamePropertyName));
		hibernateProperties.put("hibernate.connection.password", jpaProperties.getProperty(passwordPropertyName));
		hibernateProperties.put("hibernate.connection.driver_class", jpaProperties.getProperty(jdbcDriverPropertyName));
		hibernateProperties.put("hibernate.connection.provider_class", jpaProperties.getProperty(jpaProviderPropertyName));
		hibernateProperties.put("hibernate.show_sql", jpaProperties.getProperty(showSql));

		return hibernateProperties;
	}
}
