package com.capgemini.kabanos.gatherer.extractor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.capgemini.kabanos.common.configuration.Configuration;
import com.capgemini.kabanos.common.domain.Implementation;
import com.capgemini.kabanos.common.domain.Preposition;
import com.capgemini.kabanos.common.domain.Test;
import com.capgemini.kabanos.common.domain.TestFile;
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

public class JUnitExtractor implements IExtractor {

	private String[] testPrefixes = { "@Test", "@BeforeClass", "@Before", "@BeforeEach", "@BeforeAll", "@AfterClass",
			"@After", "@AfterEach", "@AfterAll" };

	@Override
	public TestFile extractFunctionsFromTestFile(String path) {

		String[] lines = FileUtils.readFile(path);

		TestFile testFile = new TestFile(path);
		Test test = null;
		int bracketCounter = 0;
		boolean isParsingTest = false;

		for (String line : lines) {

			if (isParsingTest && line.contains("}")) {
				bracketCounter--;

				if (bracketCounter == 0) {
					testFile.addTest(test);
					isParsingTest = false;
					test = null;
				}
			}

			if (isParsingTest && bracketCounter > 0) {
				test.addLine(line);
			}

			if (StringUtils.isStartsWithOneOf(line, this.testPrefixes)) {
				isParsingTest = true;
				test = new Test();
			}

			if (isParsingTest && line.contains("{")) {
				bracketCounter++;
			}
		}
		return testFile;
	}

	public List<Preposition> gatherKnowledgeFromTest(Test t) {
		List<Preposition> result = new ArrayList<Preposition>();

		String loggerLine = "";
		boolean foundLoggerStart = false, foundLoggerEnd = false;

		Preposition preposition = null;
		Preposition predecessor = null;
		Implementation implementation = new Implementation();

		// TODO ladniej!!!!!!!!!!!!!!!!!
		String loggerPrefix = Configuration.INSTANCE.getFilesConfiguration().getLoggerPrefix();
		String loggerSuffix = Configuration.INSTANCE.getFilesConfiguration().getLoggerSuffix();
		String loggerStepRegex = Configuration.INSTANCE.getFilesConfiguration().getLoggerStepRegex();

		for (String line : t.getLines()) {
			line = line.trim();

			if (foundLoggerEnd && !line.startsWith(loggerPrefix))
				if (line.length() > 0) {
					implementation.addLine(line);
				}

			// multiline logger (more that 2 lines)
			if (foundLoggerStart && !foundLoggerEnd && !line.endsWith(loggerSuffix)) {
				loggerLine += line;
			}

			// single line logger
			else if (line.startsWith(loggerPrefix)) {
				foundLoggerStart = true;
				loggerLine = line;
				foundLoggerEnd = line.endsWith(loggerSuffix);

				if (preposition != null) {
					if (implementation != null)
						preposition.addImplementation(implementation);
					result.add(preposition);
				}

				if (foundLoggerEnd) {
					predecessor = preposition;

					String key = this.extractLoggerText(loggerLine, loggerPrefix, loggerSuffix, loggerStepRegex);
					preposition = new Preposition(key, StringUtils.formatLoggerStep(key));
					
					preposition.addPredecessor(predecessor);
					implementation = new Implementation();
				}
			}

			else if (foundLoggerStart && !foundLoggerEnd && line.endsWith(loggerSuffix)) {
				loggerLine += line;
				foundLoggerEnd = true;

				if (preposition != null)
					result.add(preposition);

				predecessor = preposition;

				String key = this.extractLoggerText(loggerLine, loggerPrefix, loggerSuffix, loggerStepRegex);
				preposition = new Preposition(key, StringUtils.formatLoggerStep(key));
				
				preposition.addPredecessor(predecessor);
				implementation = new Implementation();
			}
		}

		if (preposition != null) {
			if (implementation != null)
				preposition.addImplementation(implementation);
			result.add(preposition);
		}

		return result;
	}

	public String extractLoggerText(String line, String prefix, String sufix, String loggerStepRegex) {
		line = line.trim();

		if (line.startsWith(prefix))
			line = line.replace(prefix, "");

		if (line.endsWith(sufix))
			line = line.substring(0, line.length() - sufix.length());

		line = line.trim();

		if (line.matches("^" + loggerStepRegex + ".*")) {
			Pattern p = Pattern.compile("^" + loggerStepRegex);
			Matcher m = p.matcher(line);
			line = m.replaceFirst("").trim();
		}

		line = StringUtils.formatMultiLineString(line);

		return StringUtils.formatConcatenatedVariablesInString(line);
	}
}
