package com.capgemini.kabanos.gatherer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.capgemini.kabanos.common.domain.Preposition;
import com.capgemini.kabanos.common.domain.Project;
import com.capgemini.kabanos.common.enums.ApplicationConfigType;
import com.capgemini.kabanos.common.enums.TestFrameworkType;
import com.capgemini.kabanos.common.utility.PropertiesUtils;
import com.capgemini.kabanos.common.utility.StringUtils;

public class App {
	public static void main(String[] args) throws Exception {

		//TODO for testing
		args = new String[] { "../_knowledge_tests/" };
		
		Properties properties = PropertiesUtils.loadProperties(ApplicationConfigType.GATHERER);

		String validationError = validateInputParameters(args, properties);
		if (!validationError.isEmpty()) {
			
			validationError = "ERRORS:\n"  + validationError;
			
			System.out.println(StringUtils.generateHelpMassege(validationError));
			
			printUsageHelp();
			return;
		}

		String projectName = properties.getProperty(PropertiesUtils.PROJECT_NAME);
		String testFramework = properties.getProperty(PropertiesUtils.TEST_FRAMEWORK);
		
		TestFrameworkType framework = TestFrameworkType.valueOf(testFramework.toUpperCase());

		Gatherer gatherer = new Gatherer(properties);
		List<Preposition> knowledge = gatherer.gatherKnowledge(framework, args);

		Project project = new Project(projectName, testFramework.toUpperCase());

		for (Preposition preposition : knowledge) {
			preposition.setProject(project);
		}

		gatherer.saveKnowledge(knowledge);
	}

	private static String validateInputParameters(String[] args, Properties properties) {
		StringBuilder message = new StringBuilder();
		if (args.length < 1)
			message.append("At least one path is required\n");
		try {
			TestFrameworkType.valueOf(properties.getProperty(PropertiesUtils.TEST_FRAMEWORK).toUpperCase());
		} catch (Exception e) {
			message.append("Invalid 'testFramework' parameter value\n");
		}
//TODO commented until database module upgrade
//		for(String props : PropertiesUtils.databaseParams)
//			if(properties.getProperty(props).isEmpty())
//				message.append("Empty database parameter: " + props + "\n");
		
		return message.toString();
	}

	private static void printUsageHelp() {
		List<String> message = new ArrayList<>();

		message.add("Example usage");
		message.add("    java file_name.jar PATH_1 PATH_2 PATH_3");
		message.add("");
		message.add("Where:");
		message.add("");
		message.add("PATH_n- path to the files. It can be a directory or a path to a speciffic file");
		message.add("    There is no maximum limit of provided paths, atleast one is required");
		message.add("");
		message.add("Parameters required in the '"+ PropertiesUtils.CONFIGURATION_FILE_NAME + "' file:");
		message.add("- testFramework- test framework in which the source code tests are written.");
		message.add("      Supported frameworks:");
		for (TestFrameworkType type : TestFrameworkType.values()) {
			message.add("        " + type.toString());
		}
		message.add("      NOTE: only supporter file types will be processed");
		message.add("");
		
		for(String props : PropertiesUtils.databaseParams) {
			message.add("- " + props);
		}

		System.out.println(StringUtils.generateHelpMassege(message));
	}
}