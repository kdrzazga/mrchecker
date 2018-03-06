package com.capgemini.kabanos.gatherer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.capgemini.kabanos.common.configuration.Configuration;
import com.capgemini.kabanos.common.domain.Implementation;
import com.capgemini.kabanos.common.domain.Preposition;
import com.capgemini.kabanos.common.domain.Test;
import com.capgemini.kabanos.common.domain.TestFile;
import com.capgemini.kabanos.common.enums.LanguageType;
import com.capgemini.kabanos.common.utility.ArrayUtils;
import com.capgemini.kabanos.common.utility.FileUtils;
import com.capgemini.kabanos.common.utility.StringUtils;
import com.capgemini.kabanos.database.DataBase;
import com.capgemini.kabanos.gatherer.extractor.IExtractor;
import com.capgemini.kabanos.gatherer.extractor.JavaExtractor;

public class Gatherer {

	public Gatherer() {}
	
	//parallel processing not always gives benefit. if the array is to small then parallel is not needed
	private final int PARALLEL_ARRAY_SIZE_THRESHOLD = 10;
		
		
	public List<List<Preposition>> gatherKnowledge(String path, LanguageType languageType) throws Exception {
		IExtractor extractor = null;
		switch (languageType) {
			case JAVA:
				extractor = new JavaExtractor();
				break;
			default:
				throw new Exception("Unsuported language type: " + languageType);
		}
				
		List<String> files = FileUtils.listFiles(path);

		List<TestFile> testFiles = new ArrayList<TestFile>();
		
		for(String filePath : files) {
			testFiles.add(extractor.extractFunctionsFromTestFile(filePath));
		}
		
		return createImplementationPrepositionList(testFiles);
	}

	/*
	 * list of prepositions in a list of tests
	 */
	private List<List<Preposition>> createImplementationPrepositionList(List<TestFile> testFiles) {
		Stream<TestFile> stream = testFiles.size() > PARALLEL_ARRAY_SIZE_THRESHOLD ? testFiles.parallelStream() : testFiles.stream();
		
		List<List<Preposition>> result = stream
				.map(this::extractPrepositionsFromTestFile)
				.reduce(ArrayUtils::megre)
				.orElse(new ArrayList<>());
				
		return result;
	}

	private List<List<Preposition>> extractPrepositionsFromTestFile(TestFile file) {
		return file.getTests().stream()
				.map(this::extractPrepositionsFromTest)
				.filter(el -> el.size() > 0)
				.collect(Collectors.toList());
	}

	private List<Preposition> extractPrepositionsFromTest(Test t) {
		List<Preposition> result = new ArrayList<Preposition>();
		
		Map<String, Preposition> prepositionMap = new HashMap<>();
		
		String loggerLine = "";
		boolean loggerStart = false,
				loggerEnd = false,
				gotWholeLogger = false;

		Preposition preposition = null;
		Preposition predecessor = null;
		Implementation implementation = new Implementation();
		
		//TODO ladniej!!!!!!!!!!!!!!!!!
		String loggerPrefix = Configuration.INSTANCE.getFilesConfiguration().getLoggerPrefix();
		String loggerSuffix = Configuration.INSTANCE.getFilesConfiguration().getLoggerSuffix();
		String loggerStepRegex = Configuration.INSTANCE.getFilesConfiguration().getLoggerStepRegex();
		
		for(String line : t.getLines()) {
			line = line.trim();

			if(gotWholeLogger && !line.startsWith(loggerPrefix))
				if(line.length() > 0) {
					implementation.addLine(line);
				}

			//multiline logger (more that 2 lines)
			if(loggerStart && !loggerEnd && !line.endsWith(loggerSuffix)) {
				loggerLine += line;
			}
			
			//single line logger
			else if(line.startsWith(loggerPrefix)) {
				loggerStart = true;
				loggerLine = line;
				gotWholeLogger = false;
				loggerEnd = line.endsWith(loggerSuffix);
				gotWholeLogger = loggerEnd;
				
				if(preposition != null) {
					if(implementation != null)
						preposition.addImplementation(implementation);
					result.add(preposition);
				}
				
				if(gotWholeLogger) {
					predecessor = preposition;

					String key = this.extractLoggerText(loggerLine, loggerPrefix, loggerSuffix, loggerStepRegex);
					preposition = this.getPreposition(prepositionMap, key);
						
					preposition.addPredecessor(predecessor);
					implementation = new Implementation();
				}
			}
			
			else if(loggerStart && !loggerEnd && line.endsWith(loggerSuffix)) {
				loggerLine += line;
				loggerEnd = gotWholeLogger = true;

				if(preposition != null)
					result.add(preposition);
				
				predecessor = preposition;

				String key = this.extractLoggerText(loggerLine, loggerPrefix, loggerSuffix, loggerStepRegex);
				preposition = this.getPreposition(prepositionMap, key);
				
				preposition.addPredecessor(predecessor);
				implementation = new Implementation();
			}
		}
		
		if(preposition != null) {
			if(implementation != null)
				preposition.addImplementation(implementation);
			result.add(preposition);
		}
		
		return result;
	}
	

	private Preposition getPreposition(Map<String, Preposition> prepositionMap, String perpKey) {
		Preposition preposition;
		if(prepositionMap.containsKey(perpKey)) {
			preposition = prepositionMap.get(perpKey);
		}
		else {
			preposition = new Preposition(perpKey);
			prepositionMap.put(perpKey, preposition);
		}
		return preposition;
	}
	
	public String extractLoggerText(String line, String prefix, String sufix, String loggerStepRegex) {		
		line = line.trim();
		
		if(line.startsWith(prefix))
			line = line.replace(prefix, "");
		
		if(line.endsWith(sufix))
			line = line.substring(0, line.length() - sufix.length());
		
		line = line.trim();

		if(line.matches("^"+loggerStepRegex+".*")) {
			Pattern p = Pattern.compile("^"+loggerStepRegex);
			Matcher m = p.matcher(line);
			line = m.replaceFirst("").trim();	
		}
		
		return StringUtils.formatLoggerStep(line);
	}
	
	public boolean saveKnowledge(List<Preposition> knowledge) {
		return new DataBase().savePrepositions(knowledge);
	}
}