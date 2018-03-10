package com.capgemini.kabanos.database;

import java.util.List;
import java.util.Properties;

import com.capgemini.kabanos.common.domain.Preposition;
import com.capgemini.kabanos.common.enums.ApplicationConfigType;
import com.capgemini.kabanos.common.utility.PropertiesUtils;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
		DataBase db = null;
		try {
			
			Properties p = PropertiesUtils.loadProperties(ApplicationConfigType.GATHERER);
			db = new DataBase(p);

			List<Preposition> result = db.getPrepositions(p.getProperty(PropertiesUtils.PROJECT_NAME));

			System.out.println(result);

		} finally {
			if (db != null)
				db.shutdown();

		}
	}
}
