package com.capgemini.kabanos.generator;

import com.capgemini.kabanos.common.domain.Implementation;
import com.capgemini.kabanos.common.domain.Preposition;
import com.capgemini.kabanos.common.domain.Step;
import com.capgemini.kabanos.common.domain.Test;
import com.capgemini.kabanos.common.enums.SourceType;
import com.capgemini.kabanos.common.utility.FileUtils;
import com.capgemini.kabanos.common.utility.PropertiesUtils;
import com.capgemini.kabanos.common.utility.StringUtils;
import com.capgemini.kabanos.database.DataBase;
import com.capgemini.kabanos.generator.mock.MockJiraTestGenerator;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class Generator {

	private DataBase database;
	private Properties properties;

	public Generator(Properties properties) {
		this.properties = properties;
	}

	private Template initTemplate() throws IOException {
		freemarker.template.Configuration cfg = new freemarker.template.Configuration();
		cfg.setDirectoryForTemplateLoading(new File(this.properties.getProperty(PropertiesUtils.TEMPLATE_PATH)));
		cfg.setIncompatibleImprovements(new Version(2, 3, 20));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setLocale(Locale.US);
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		return cfg.getTemplate(this.properties.getProperty(PropertiesUtils.TEMPLATE_FILE_NAME));
	}

	private Map<String, Object> initTempleteInput(Test test) {
		Map<String, Object> input = new HashMap<String, Object>();

		input.put("testName", test.getName());
		input.put("testSteps", test.getSteps());

		return input;
	}

	/**
	 * Function generates a template for the test assigned to the testId form the input. 
	 * If the connected database contains any information about the implementation of
	 * any of the steps assigned to the test then automatically that implementation is
	 * concatenated right under the test step- implementation preposition
	 * 
	 * @param testId id of the test with steps in the given source tool eg. TASK_ID in JIRA, QC_ID in HP ALM tool
	 * @param sourceType source from which the test information will be taken eg. JIRA, HP ALM
	 * @return String representation of the generated template
	 * @throws IOException
	 * @throws TemplateException
	 */
	public String generateTemplate(String testId, SourceType sourceType) throws IOException, TemplateException {
		Test test = this.getTestFromSource(testId, sourceType);
		this.addPrepositions(test);

		Template template = this.initTemplate();

		Map<String, Object> input = this.initTempleteInput(test);

		StringWriter stringWriter = new StringWriter();
		template.process(input, stringWriter);

		return stringWriter.toString();
	}

	private Test getTestFromSource(String testId, SourceType sourceType) {

		// IConnector connector = null;
		//
		// switch (sourceType) {
		// case JIRA:
		// connector = new
		// JiraConnector(Configuration.INSTANCE.getJiraConfiguration().getJiraUrl());
		// break;
		// case QC:
		// break;
		// default:
		// break;
		//
		// }

		// return connector.getTestData(testId);

		// chwilowe rozwiazanie
		// brak JIRA, wiec trzeba sobie radzic :P
		return MockJiraTestGenerator.generateTest();
	}

	private void addPrepositions(Test test) {

		this.database = new DataBase(this.properties);

		for (Step step : test.getSteps()) {

			Preposition prep = database.getPreposition(this.properties.getProperty(PropertiesUtils.PROJECT_NAME),
					StringUtils.formatLoggerStep(step.getDescription()));

			List<Implementation> impls = new ArrayList<>();

			if (prep != null) {
				impls.addAll(prep.getImplementations());
				impls.sort((l, r) -> r.getOccurrences() - l.getOccurrences());
			}

			step.setImplementations(impls);
		}

		this.database.shutdown();
	}

	
	/**
	 * function saved the given Sting representing the file content under the given location
	 * 
	 * @param path location of the folder in which the generated template should be saved
	 * @param fileName name of the file with extension
	 * @param template String representing the generated template
	 * @return true is save operation ended with success, false in ended with failure 
	 * @throws IOException 
	 */
	public boolean saveTemplate(String path, String fileName, String template) throws IOException {
		FileUtils.createDirectory(path);
		return FileUtils.saveFile(path + "/" + fileName, template);
	}

	/**
	 * function generates a file name matching the below format
	 *   <br>
	 *     template_20180313221933.txt
	 * <br>
	 * where:<br>
	 *     template_ - constant value of the generated file name<br>
	 *     20180313221933 - timestamp matching format yyyyMMddHHmmss<br>
	 *     .txt - fileExtension from the input
	 * 
	 * @param fileExtension extension of the file eg. java, js, cpp etc.
	 * @return
	 */
	public String generateFileName(String fileExtension) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return "template_" + dateFormat.format(new Date()) + "." + fileExtension;
	}
}