package com.capgemini.kabanos.database;

import java.util.List;

import com.capgemini.kabanos.common.domain.Preposition;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
		DataBase db = null;
		try {
			db = new DataBase(null);

			List<Preposition> result = db.getPrepositions("kabanos");

			System.out.println(result);

		} finally {
			if (db != null)
				db.shutdown();

		}
	}
}
