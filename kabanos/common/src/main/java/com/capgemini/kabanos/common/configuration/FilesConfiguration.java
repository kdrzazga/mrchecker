package com.capgemini.kabanos.common.configuration;

public class FilesConfiguration {
	private String knowledgePath;
	private String templatePath;
	private String loggerPrefix;
	private String loggerSuffix;
	private String loggerStepRegex;	
	

	public String getLoggerPrefix() {
		return loggerPrefix;
	}

	public void setLoggerPrefix(String loggerPrefix) {
		this.loggerPrefix = loggerPrefix;
	}

	public String getLoggerSuffix() {
		return loggerSuffix;
	}

	public void setLoggerSuffix(String loggerSuffix) {
		this.loggerSuffix = loggerSuffix;
	}

	public String getLoggerStepRegex() {
		return loggerStepRegex;
	}

	public void setLoggerStepRegex(String loggerStepRegex) {
		this.loggerStepRegex = loggerStepRegex;
	}
	
	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public String getKnowledgePath() {
		return knowledgePath;
	}

	public void setKnowledgePath(String knowledgePath) {
		this.knowledgePath = knowledgePath;
	}
}
