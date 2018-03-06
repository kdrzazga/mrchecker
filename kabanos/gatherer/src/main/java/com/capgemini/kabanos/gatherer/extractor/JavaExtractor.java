package com.capgemini.kabanos.gatherer.extractor;

import java.io.IOException;

import com.capgemini.kabanos.common.domain.Test;
import com.capgemini.kabanos.common.domain.TestFile;
import com.capgemini.kabanos.common.enums.LanguageType;
import com.capgemini.kabanos.common.utility.FileUtils;
import com.capgemini.kabanos.common.utility.StringUtils;


/*
 * pomysly:
 * 	zrobic liste nazw funkcji w klasie -
 * 	  jesli funkcja jest uzyta w tescie to wljek jej kod jako stepy
 *      problemy? rozne nazwy zmiennych :(
 *      
 * 
 * problemy:
 *   znaki { i } moga byc zaszyte w stringach lub w komentarzach
 */

public class JavaExtractor implements IExtractor {

	private LanguageType JAVA = LanguageType.JAVA;
	
	@Override
	public TestFile extractFunctionsFromTestFile(String path) throws IOException { //czy ma byc throws???
				
		String[] lines = FileUtils.readFile(path);
		
		TestFile testFile = new TestFile();
		Test test = null;
		int bracketCounter = 0;
		boolean isParsingTest = false;

		for(String line : lines) {
			
			if(isParsingTest && line.contains("}")) {
				bracketCounter--;
				
				if(bracketCounter == 0) {
					testFile.addTest(test);
					isParsingTest = false;
					test = null;
				}
			}
			
			if(isParsingTest && bracketCounter > 0) {
				test.addLine(line);
			}
			
			if(StringUtils.isStartsWithOneOf(line, this.JAVA.getTestPrefixes())) {
				isParsingTest = true;
				test = new Test();
			}
			
			if(isParsingTest && line.contains("{")) {
				bracketCounter++;
			}
		}
		return testFile;
	}
}
