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
    	
    	    	
    	String path = "../_knowledge_tests";
    	  	
    	Gatherer gatherer = new Gatherer();
    	
    	List<List<Preposition>> knowledge = gatherer.gatherKnowledge(path, LanguageType.JAVA);
    	
    	
    	for(List<Preposition> g : knowledge)
    		gatherer.saveKnowledge(g);
    }
}