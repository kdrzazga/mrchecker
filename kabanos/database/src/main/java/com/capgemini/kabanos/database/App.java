package com.capgemini.kabanos.database;

import java.util.Arrays;
import java.util.List;

import com.capgemini.kabanos.common.domain.Preposition;
//import com.capgemini.kabanos.common.domain.Project;
import com.capgemini.kabanos.common.enums.TestFrameworkType;
import com.capgemini.kabanos.gatherer.Gatherer;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws Exception
    {
    	System.out.println("sdasd");
    	
    	args = new String[] { "kabanos", "junit", "../_knowledge_tests/" };
	
		String[] paths = Arrays.copyOfRange(args, 2, args.length);

		TestFrameworkType framework = TestFrameworkType.valueOf(args[1].toUpperCase());

		Gatherer gatherer = new Gatherer();
		List<Preposition> knowledge = gatherer.gatherKnowledge(framework, paths);

//		Project project = new Project(args[0], args[1].toUpperCase());

//		for (Preposition preposition : knowledge) {
//			preposition.setProject(project);
//		}

		new DataBase().savePrepositions(knowledge);
		
//		gatherer.saveKnowledge(knowledge);
    	
    	
//        DataBase db = new DataBase();
//          
//        List<Preposition> result = db.getAllPrepositions();
        
        
//        db.savePreposition(result);
        
//        System.out.println(preposi);
    }
}
