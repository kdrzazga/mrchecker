package com.capgemini.kabanos.testSource.connector.jira;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.api.domain.User;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;
import com.capgemini.kabanos.common.domain.Step;
import com.capgemini.kabanos.common.domain.Test;
import com.capgemini.kabanos.common.exception.LoginException;
import com.capgemini.kabanos.common.utility.PropertiesUtils;
import com.capgemini.kabanos.testSource.connector.IConnector;


//TODO sprawdzic czy dziala po refaktorze!!!!!!!!!!!!
public class JiraConnector implements IConnector {

	private Properties properties;
	private String jiraUrl;
	private JiraRestClient jiraClient;

	public JiraConnector(Properties properties) {
		this.properties = properties;
	}

	public boolean login(String user, String password) throws LoginException {
		try {
			this.jiraClient = createJiraRestClient(user, password);
			// verify login operation

			Promise<User> promise = this.jiraClient.getUserClient().getUser(user);
			User loggedUser = promise.claim();

			return loggedUser.getName().equals(user);
		} catch (Exception e) {
			throw new LoginException("Unable to login to JIRA url: " + this.jiraUrl + "\nas user: " + user);
		}
	}

	protected JiraRestClient createJiraRestClient(String user, String password) throws URISyntaxException {
		JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
		URI uri = new URI(this.jiraUrl);
		return factory.createWithBasicHttpAuthentication(uri, user, password);
	}
	
	public Test getTestData(String testId) {
		if (this.jiraClient == null) {
			// TODO add lines
		}

		Promise<Issue> issuePromisse = this.jiraClient.getIssueClient().getIssue(testId);
		Issue issue = issuePromisse.claim();

		return this.issueToTest(issue);
	}

	public List<Test> getTestsData(List<String> testIdList) {
		String jql = "key in (" + String.join(",", testIdList.toArray(new String[] {})) + ")";
		Promise<SearchResult> searchJqlPromise = jiraClient.getSearchClient().searchJql(jql);
		List<Test> result = new ArrayList<>();

		for (Issue issue : searchJqlPromise.claim().getIssues()) {
			result.add(this.issueToTest(issue));
		}
		return result;
	}

	private Test issueToTest(Issue issue) {
		Test result = new Test();
		result.setName(issue.getSummary());
		result.setSteps(this.extractTestSteps(issue.getDescription()));
		return result;
	}

	private List<Step> extractTestSteps(String issueDescription) {

		List<Step> result = new ArrayList<Step>();
		String[] lines = issueDescription.split(System.lineSeparator());

		String prefix = this.properties.getProperty(PropertiesUtils.JIRA_TEST_PREFIX_REGEX);
		String suffix = this.properties.getProperty(PropertiesUtils.JIRA_TEST_SUFFIX_REGEX);

		String startRegex = "^" + prefix + ".*";
		String endRegex = "^.*" + suffix + "$";

		for (String line : lines) {
			if (line.matches(startRegex) && line.matches(endRegex)) {
				line = line.replaceFirst(prefix, "").trim();
				line = line.substring(0, line.length() - suffix.length()).trim();
				result.add(new Step(line));
			}
		}

		return result;
	}
}
