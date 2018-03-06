package com.capgemini.kabanos.generator;

import com.capgemini.kabanos.common.configuration.Configuration;
import com.capgemini.kabanos.common.domain.Implementation;
import com.capgemini.kabanos.common.domain.Preposition;
import com.capgemini.kabanos.common.domain.Step;
import com.capgemini.kabanos.common.domain.Test;
import com.capgemini.kabanos.common.enums.SourceType;
import com.capgemini.kabanos.common.utility.StringUtils;
import com.capgemini.kabanos.database.DataBase;
import com.capgemini.kabanos.generator.mock.MockJiraTestGenerator;
import com.capgemini.kabanos.testSource.connector.IConnector;
import com.capgemini.kabanos.testSource.connector.jira.JiraConnector;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class Generator {

	private DataBase database;

	public Generator() {
		this.database = new DataBase();
	}

	public String generateTemplate(String testId, SourceType sourceType) throws IOException, TemplateException {
		
		Test test = this.getTestFromSource(testId, sourceType);

		this.generatePrepositions(test);

		
		freemarker.template.Configuration cfg = new freemarker.template.Configuration();
		cfg.setClassForTemplateLoading(Generator.class, "../../../../templates");

		cfg.setIncompatibleImprovements(new Version(2, 3, 20));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setLocale(Locale.US);
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		Map<String, Object> input = new HashMap<String, Object>();

		input.put("testName", test.getName());
		input.put("testSteps", test.getSteps());

		
		Template template = cfg.getTemplate("template.ftl");

		StringWriter stringWriter = new StringWriter();
		template.process(input, stringWriter);

		System.out.println(stringWriter);

		return null;
	}

	private Test getTestFromSource(String testId, SourceType sourceType) {

		@SuppressWarnings("rawtypes")
		IConnector connector = null;

		switch (sourceType) {
		case JIRA:
			connector = new JiraConnector(Configuration.INSTANCE.getJiraConfiguration().getJiraUrl());
			break;
		case QC:
			break;
		default:
			break;

		}

		// return connector.getTestData(testId);

		// chwilowe rozwiazanie
		// brak JIRA, wiec trzeba sobie radzic :P
		return MockJiraTestGenerator.generateTest();
	}

	private void generatePrepositions(Test test) {
		for (Step step : test.getSteps()) {

			Preposition prep = database.getPreposition(StringUtils.formatLoggerStep(step.getDescription()));

			List<Implementation> impls = new ArrayList<>();

			if (prep != null) {
				impls.addAll(prep.getImplementations());
				impls.sort((l, r) -> {
					return r.getOccurrences() - l.getOccurrences();
				});
			}
			
			step.setImplementations(impls);
		}
	}
}