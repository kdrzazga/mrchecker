package com.capgemini.kabanos.database;

import java.util.List;

import com.capgemini.kabanos.common.domain.Preposition;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws Exception
    {
    	
        DataBase db = new DataBase();
          
        List<Preposition> result = db.getPrepositions("kabanos");
        
        System.out.println(result);
    }
}
