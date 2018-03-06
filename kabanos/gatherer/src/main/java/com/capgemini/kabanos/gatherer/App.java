package com.capgemini.kabanos.gatherer;

import java.util.List;

import com.capgemini.kabanos.common.domain.Preposition;
import com.capgemini.kabanos.common.enums.LanguageType;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
    	
    	System.out.println("----");
    	
//    	C:\Users\masokol\workspace\devonfw-testing\kabanos\_knowledge_tests
    	
    	String path = "C:\\Users\\masokol\\workspace\\devonfw-testing\\kabanos\\_knowledge_tests\\";
    	  	
    	Gatherer gatherer = new Gatherer();
    	
    	List<List<Preposition>> knowledge = gatherer.gatherKnowledge(path, LanguageType.JAVA);
    	
    	
//    	gatherer.saveKnowledge(knowledge.get(4));
    	
    	for(List<Preposition> g : knowledge)
    	gatherer.saveKnowledge(g);
    }
}