package com.capgemini.kabanos.common.utility;

import java.io.FileInputStream;
import java.util.Properties;

import com.capgemini.kabanos.common.enums.ApplicationConfigType;
import com.capgemini.kabanos.common.exception.PropertiesException;

public class PropertiesUtils {

	public static final String CONFIGURATION_FILE_NAME = "app.properties";

	public static final String PROJECT_NAME = "projectName";
	public static final String PROJECT_DESCRIPTION = "projectDescription";
	public static final String TEST_FRAMEWORK = "testFramework";
	public static String[] projectProperties = { PROJECT_NAME, PROJECT_DESCRIPTION, TEST_FRAMEWORK };
	
	public static String[] databaseParams = { "dbUrl", "dbName", "dbUser", "dbPassword" };

	public static final String LOGGER_PREFIX = "loggerPrefix";
	public static final String LOGGER_SUFFIX = "loggerSuffix";
	public static final String LOGGER_STEP_REGEX = "loggerStepRegex";
	public static String[] gathererParams = { LOGGER_PREFIX, LOGGER_SUFFIX, LOGGER_STEP_REGEX };

	public static final String TEMPLATE_PATH = "templatePath";
	public static final String TEMPLATE_FILE_NAME = "templateFileName";
	public static String[] templateParams = { TEMPLATE_PATH, TEMPLATE_FILE_NAME };
	
	
	public static String[] jiraParams = { "jiraUrl", "jiraUser", "jiraPassword", "jiraTestStartLine",
			"jiraTestStepPrefixRegex", "jiraTestStepSuffixRegex" };

	private static void generateEmptyProperties(ApplicationConfigType type) {
		try {
			StringBuilder emptyProperties = new StringBuilder();

			emptyProperties.append("#Default properties- fill these values in order to use ").append(type)
					.append("\n\n");

			emptyProperties.append("#Configuration related to project\n");
			addEmptyParams(emptyProperties, projectProperties);

			emptyProperties.append("#Configuration related to database connection\n");
			addEmptyParams(emptyProperties, databaseParams);

			switch (type) {
			case GATHERER:
				emptyProperties.append("#Configuration related to code lines that contain test steps\n");
				addEmptyParams(emptyProperties, gathererParams);
				break;
			case GENERATOR:
				emptyProperties.append("#Configuration related th the template location\n");
				addEmptyParams(emptyProperties, templateParams);

				emptyProperties.append("#Configuration related to the JIRA application");
				addEmptyParams(emptyProperties, jiraParams);
				break;
			case GRAPHOTRON:
				break;
			default:
				break;
			}

			FileUtils.saveFile(CONFIGURATION_FILE_NAME, emptyProperties.toString());
		} catch (Exception e) {
			throw new PropertiesException("Exception while generating default configuration file " + CONFIGURATION_FILE_NAME + ":\n" + e);
		}
	}

	private static void addEmptyParams(StringBuilder properties, String[] names) {
		for (String name : names)
			properties.append(name).append("=\n");

		properties.append("\n");
	}

	public static Properties loadProperties(ApplicationConfigType type) {
		try {
			Properties defaultProps = new Properties();
			FileInputStream in = new FileInputStream(CONFIGURATION_FILE_NAME);
			
			defaultProps.load(in);
			in.close();

			return defaultProps;
		} 
		catch (java.io.FileNotFoundException e) {
			System.out.println(e);
			
			generateEmptyProperties(type);
			
			throw new PropertiesException("\n\nMissing configuration file. Generated a scaffold template\n\n");
		} 
		catch (Exception e) {
			
			throw new PropertiesException(
					"Exception while loadind properties file: " + CONFIGURATION_FILE_NAME + "\n\n" + e);
		}
	}
}