package com.capgemini.kabanos.database.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;

import com.capgemini.kabanos.common.utility.PropertiesUtils;

public class HibernateUtils {

	private static StandardServiceRegistry registry;
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory(Properties properties) {
		if (sessionFactory == null) {
			try {

				Map<String, String> m = new HashMap<>();
				m.put(AvailableSettings.URL, properties.getProperty(PropertiesUtils.DB_URL));
				m.put(AvailableSettings.USER, properties.getProperty(PropertiesUtils.DB_USER));
				m.put(AvailableSettings.PASS, properties.getProperty(PropertiesUtils.DB_PSWD));

				registry = new StandardServiceRegistryBuilder().configure().applySettings(m).build();

				// Create SessionFactory
				sessionFactory = new MetadataSources(registry).getMetadataBuilder().build().getSessionFactoryBuilder()
						.build();

			} catch (Exception e) {
				e.printStackTrace();
				if (registry != null) {
					StandardServiceRegistryBuilder.destroy(registry);
				}
			}
		}
		return sessionFactory;
	}

	public static void shutdown() {
		if (registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}

	private HibernateUtils() {}
}