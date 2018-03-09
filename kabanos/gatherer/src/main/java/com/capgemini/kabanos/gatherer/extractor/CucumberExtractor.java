package com.capgemini.kabanos.gatherer.extractor;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.kabanos.common.domain.Preposition;
import com.capgemini.kabanos.common.domain.Test;
import com.capgemini.kabanos.common.domain.TestFile;
import com.capgemini.kabanos.common.utility.FileUtils;
import com.capgemini.kabanos.common.utility.StringUtils;

public class CucumberExtractor implements IExtractor {

	private String testStarter = "Scenario";
	private String[] testTerminators = { "Feature", "Background", "Description", testStarter };

	private String[] stepPrefixes = {"Given", "When", "Then", "And", "But", "*"};
	
	
	@Override
	public TestFile extractFunctionsFromTestFile(String path) {
		String[] lines = FileUtils.readFile(path);

		TestFile testFile = new TestFile(path);
		
		String testName = "";
		Test test = null;
		boolean isParsingTest = false;

		for (String line : lines) {

			line = line.trim();
			
			if(StringUtils.isStartsWithOneOf(line, testTerminators)) {
				isParsingTest = false;
				if(test != null) {
					testFile.addTest(test);
					test = null;
				}
			}
			
			if(isParsingTest && !line.isEmpty()) {
				test.addLine(line);
			}
			
			
			if (line.trim().startsWith(testStarter)) {
				isParsingTest = true;
				testName = line.trim();//TODO extract
				test = new Test(testName);
			}
		}
		if(test != null)
			testFile.addTest(test);
		
		return testFile;
	}

	@Override
	public List<Preposition> gatherKnowledgeFromTest(Test test) {

		List<Preposition> result = new ArrayList<>();
		
		Preposition predecessor = null; 
		
		for(String line : test.getLines()) {
			if(StringUtils.isStartsWithOneOf(line, this.stepPrefixes)) {	
				Preposition preposition = new Preposition(line, StringUtils.formatLoggerStep(line));

				if(predecessor != null) {
					preposition.addPredecessor(predecessor);
				}

				result.add(preposition);
				predecessor = preposition;
			}
		}

		return result;
	}
}
