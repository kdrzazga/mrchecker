package com.capgemini.kabanos.dynamicPseudoCucumber;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.capgemini.kabanos.common.domain.Preposition;
import com.capgemini.kabanos.database.DataBase;
import com.capgemini.kabanos.dynamicPseudoCucumber.domain.StepExecutionResult;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
		DataBase db = new DataBase();
		
		List<Preposition> preps = db.getAllPrepositions();	
        
        
		
		List<String> input = preps.stream().map(el -> el.getLoggerStep()).collect(Collectors.toList());
		
		Map<String, StepExecutionResult> report = new TestExecutor().generateReport(input);
    
    
		for(String g : input) {

			System.out.println(g);	

			System.out.println("\t" + report.get(g).getState());
			System.out.println("\t\t\t\t\t" + report.get(g).getMessage());
			
			System.out.println("  ");
		}
		
		
		System.out.println("??");
    }
}