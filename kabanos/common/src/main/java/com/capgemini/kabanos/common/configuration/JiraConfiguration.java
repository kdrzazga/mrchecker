package com.capgemini.kabanos.common.configuration;

public class JiraConfiguration {
	
	private String jiraUrl;
	private String jiraUser;
	private String jiraPassword;
	private String testStartLine;
	private String testStepPrefixRegex;
	private String testStepSuffixRegex;
	
	public String getJiraUser() {
		return jiraUser;
	}
	public void setJiraUser(String jiraUser) {
		this.jiraUser = jiraUser;
	}
	public String getJiraPassword() {
		return jiraPassword;
	}
	public void setJiraPassword(String jiraPassword) {
		this.jiraPassword = jiraPassword;
	}
	public String getJiraUrl() {
		return jiraUrl;
	}
	public void setJiraUrl(String jiraUrl) {
		this.jiraUrl = jiraUrl;
	}
	public String getTestStepPrefixRegex() {
		return testStepPrefixRegex;
	}
	public void setTestStepPrefixRegex(String testStepPrefixRegex) {
		this.testStepPrefixRegex = testStepPrefixRegex;
	}
	public String getTestStepSuffixRegex() {
		return testStepSuffixRegex;
	}
	public void setTestStepSuffixRegex(String testStepSuffixRegex) {
		this.testStepSuffixRegex = testStepSuffixRegex;
	}
	public String getTestStartLine() {
		return testStartLine;
	}
	public void setTestStartLine(String testStartLine) {
		this.testStartLine = testStartLine;
	}
}
