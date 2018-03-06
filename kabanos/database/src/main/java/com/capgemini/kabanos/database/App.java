package com.capgemini.kabanos.database;


/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws Exception
    {
        DataBase db = new DataBase();//Configuration.INSTANCE.getDbConfiguration());
          
        System.out.println(db.getAllPrepositions());
    }
}
