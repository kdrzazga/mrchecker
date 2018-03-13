package com.capgemini.kabanos.generator;

import java.util.Properties;
import com.capgemini.kabanos.common.enums.ApplicationConfigType;
import com.capgemini.kabanos.common.enums.SourceType;
import com.capgemini.kabanos.common.utility.PropertiesUtils;
import com.capgemini.kabanos.common.utility.StringUtils;

public class App {
	public static void main(String[] args) throws Exception {
		args = new String[] { "JIRA", "KAB-1" };

		if (!validateInputParameters(args)) {
			return;
		}

		Properties properties = PropertiesUtils.loadProperties(ApplicationConfigType.GENERATOR);

		Generator generator = new Generator(properties);
		String template = generator.generateTemplate(args[1], SourceType.valueOf(args[0].toUpperCase()));

		String path = properties.getProperty(PropertiesUtils.TEMPLATE_OUTPUT_FILE_PATH);
		String fileExtension = properties.getProperty(PropertiesUtils.TEMPLATE_EXTENSION);

		if (fileExtension == null || fileExtension.isEmpty()) {
			fileExtension = "txt";
		} else {
			fileExtension = fileExtension.toLowerCase();
		}

		String fileName = generator.generateFileName(fileExtension);

		boolean savedFile = generator.saveTemplate(path, fileName, template);

		if (savedFile) {
			System.out.println("saved generated template under: " + path + "/" + fileName);
		} else {
			System.out.println("Unable to save templete under given path: " + path);
		}
	}

	private static boolean validateInputParameters(String[] params) {
		StringBuilder sb = new StringBuilder();
		if (params.length < 2) {
			sb.append("minimum two input parameters are required\n");
		}

		try {
			SourceType.valueOf(params[0].toUpperCase());
		} catch (Exception e) {
			sb.append("Invalid 'SourceType' parameter value\n");
		}

		String errorMessage = sb.toString();

		if (!errorMessage.isEmpty()) {
			System.out.println(StringUtils.generateHelpMassege(errorMessage, true));

			printUsage();
		}
		return errorMessage.isEmpty();
	}

	private static void printUsage() {
		// TODO Auto-generated method stub
	}
}
