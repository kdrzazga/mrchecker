package com.capgemini.kabanos.gatherer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.capgemini.kabanos.common.domain.Preposition;
import com.capgemini.kabanos.common.domain.Project;
import com.capgemini.kabanos.common.enums.TestFrameworkType;
import com.capgemini.kabanos.common.utility.StringUtils;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {

		// for testing
		args = new String[] { "kabanos", "junit", "../_knowledge_tests/" };

		if (!validateInputParameters(args)) {
			printUsageHelp();
			return;
		}

		String[] paths = Arrays.copyOfRange(args, 2, args.length);

		TestFrameworkType framework = TestFrameworkType.valueOf(args[1].toUpperCase());

		Gatherer gatherer = new Gatherer();
		List<Preposition> knowledge = gatherer.gatherKnowledge(framework, paths);

		Project project = new Project(args[0], args[1].toUpperCase());

		for (Preposition preposition : knowledge) {
			preposition.setProject(project);
		}

		gatherer.saveKnowledge(knowledge);
	}

	private static boolean validateInputParameters(String[] args) {
		if (args.length < 3)
			return false;

		try {
			TestFrameworkType.valueOf(args[1].toUpperCase());
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	private static void printUsageHelp() {
		List<String> message = new ArrayList<>();

		message.add("Example usage");
		message.add("    java file_name.jar PROJECT_NAME LANGUAGE_TYPE PATH_1 PATH_2 PATH_3");
		message.add("");
		message.add("Where:");
		message.add("PROJECT_NAME- name of the project");
		message.add("");
		message.add("TEST_TYPE- test framework in which the source code tests are written.");
		message.add("    Supported frameworks:");
		message.add("        JUNIT- only *.java files");
		message.add("        CUCUMBER- only *.feature files");
		message.add("");
		message.add("PATH_n- path to the files. It can be a directory or a path to a speciffic file");
		message.add("    There is no maximum limit of provided paths");
		message.add("");
		message.add("Minimum three input parameters are required: PROJECT_NAME, LANGUAGE_TYPE and at least one PATH");

		System.out.println(StringUtils.generateHelpMassege(message));
	}
}