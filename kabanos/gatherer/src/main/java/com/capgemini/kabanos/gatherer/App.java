package com.capgemini.kabanos.gatherer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.capgemini.kabanos.common.domain.Preposition;
import com.capgemini.kabanos.common.enums.LanguageType;
import com.capgemini.kabanos.common.utility.StringUtils;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) throws Exception {    	    	

    	//for testing
    	args = new String[] {
    			"java", "../_knowledge_tests/", "../_knowledge_tests/one.java"
    	};
    	
    	if(!validateInputParameters(args)) {
    		printUsageHelp();
    		return;
    	}

    	String[] paths = Arrays.copyOfRange(args, 1, args.length);

    	LanguageType language = LanguageType.valueOf(args[0].toUpperCase());
    	
    	Gatherer gatherer = new Gatherer();    	
    	List<List<Preposition>> knowledge = new ArrayList<>();
    	
   		knowledge.addAll(gatherer.gatherKnowledge(language, paths));
    	
    	for(List<Preposition> g : knowledge) {
    		gatherer.saveKnowledge(g);
    	}
    }
    
    
    private static boolean validateInputParameters(String[] args) {
    	if(args.length < 2)
    		return false;
    	
    	//validate language type
    	try {
    		LanguageType.valueOf(args[0].toUpperCase());
    	} catch(Exception e) {
    		return false;
    	}
    	    	
    	return true;
    }
    
    private static void printUsageHelp() {

    	List<String> message = new ArrayList<>();
    	
    	message.add("Example usage");
    	message.add("    java file_name.jar LANGUAGE_TYPE PATH_1 PATH_2 PATH_3");
    	message.add("");
    	message.add("Where:");
    	message.add("LANGUAGE_TYPE- language in which the source code tests are written.");
    	message.add("    Supported languages: JAVA");
    	message.add("");
    	message.add("PATH_n- path to the files. It can be a directory or a path to a speciffic file");
    	message.add("    There is no maximum limit of provided paths");
    	message.add("");
    	message.add("Minimum two input parameters are required: LANGUAGE_TYPE and at least one PATH");
    	
    	String formattedMessage = StringUtils.generateHelpMassege(message);

    	System.out.println(formattedMessage);
    }
}