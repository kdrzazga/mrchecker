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
 *   pomysl na rozwiazanie- przed sprawdzeniem czy } lub { znajduje sie w lini nalezy usunac
 *   wszystkie stringi z linii
 */

public class JavaExtractor implements IExtractor {

	@Override
	public TestFile extractFunctionsFromTestFile(String path) {
				
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
			
			if(StringUtils.isStartsWithOneOf(line, LanguageType.JAVA.getTestPrefixes())) {
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
