package com.capgemini.kabanos.common.configuration;

public enum Configuration {
	
	INSTANCE;
	
	private JiraConfiguration jiraConfiguration;
	
//	private FilesConfiguration filesConfiguration;
	
	private DbConfiguration dbConfiguration;

	Configuration() {
		this.setJiraConfiguration();
//		this.setFilesConfiguration();
		this.setDbConfiguration();
	}
	
	
//	private void setFilesConfiguration() {
//		this.filesConfiguration = new FilesConfiguration();
//		this.filesConfiguration.setKnowledgePath("C:\\Users\\masokol\\workspace\\devonfw-testing\\allure-app-under-test\\src\\test\\java\\com\\capgemini\\ntc\\selenium\\features\\webElements\\mockTest.java");
//		this.filesConfiguration.setTemplatePath("../../../../templates");
//		this.filesConfiguration.setTemplateName("template.ftl");
//		
//		this.filesConfiguration.setLoggerPrefix("Logger.log(\"");
//		this.filesConfiguration.setLoggerSuffix("\");");
//
//		this.filesConfiguration.setLoggerStepRegex("");
//		
//	}


	private void setJiraConfiguration() {
		this.jiraConfiguration = new JiraConfiguration();

		this.jiraConfiguration.setJiraUrl("http://localhost:8080");
		this.jiraConfiguration.setJiraUser("myAdmin");
		this.jiraConfiguration.setJiraPassword("haslo");
		this.jiraConfiguration.setTestStepPrefixRegex("STEP \\d+(.)");
		this.jiraConfiguration.setTestStepSuffixRegex("");
	}
	
	private void setDbConfiguration() {
		this.dbConfiguration = new DbConfiguration();
		
		this.dbConfiguration.setDbUrl("localhost");
		this.dbConfiguration.setPort(27017);
		this.dbConfiguration.setDbName("kabanos_impl1");
		this.dbConfiguration.setCollectionName("prepositions");
		
		
	}
	public JiraConfiguration getJiraConfiguration() {
		return jiraConfiguration;
	}

	public void setJiraConfiguration(JiraConfiguration jiraConfiguration) {
		this.jiraConfiguration = jiraConfiguration;
	}


	public DbConfiguration getDbConfiguration() {
		return dbConfiguration;
	}

	public void setDbConfiguration(DbConfiguration dbConfiguration) {
		this.dbConfiguration = dbConfiguration;
	}
}
