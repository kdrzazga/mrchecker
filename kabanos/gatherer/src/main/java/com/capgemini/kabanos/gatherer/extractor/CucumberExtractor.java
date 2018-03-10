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
				test = new Test(this.extractScenarioName(testName));
			}
		}
		if(test != null)
			testFile.addTest(test);
		
		return testFile;
	}

	private String extractScenarioName(String line) {
		if(line.trim().startsWith(this.testStarter)) {
			return line.replaceFirst(this.testStarter, "").trim();
		}
		return line;
	}
	
	@Override
	public List<Preposition> gatherKnowledgeFromTest(Test test) {

		List<Preposition> result = new ArrayList<>();
		Preposition preposition = null, predecessor = null; 
		
		for(String line : test.getLines()) {
			if(StringUtils.isStartsWithOneOf(line, this.stepPrefixes)) {
				
				line = this.extractGherkinSentence(line);
				
				preposition = new Preposition(line, StringUtils.formatLoggerStep(line));

				if(predecessor != null) {
					preposition.addPredecessor(predecessor);
				}

				result.add(preposition);
				predecessor = preposition;
			}
		}

		//step can be last in one test, but in the middle of a different test
		if(preposition != null) {
			result.get(0).setWasFirstStep(true);
			preposition.setWasLastStep(true);
		}
		
		return result;
	}

	private String extractGherkinSentence(String line) {
		line = line.trim();
		for(String prefix : this.stepPrefixes) {
			if(line.startsWith(prefix))
				return line.replaceFirst(prefix, "").trim();
		}
		return line;
	}
}