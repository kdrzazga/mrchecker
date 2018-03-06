package com.capgemini.kabanos.testSource.connector;

import java.util.List;

import com.capgemini.kabanos.common.domain.Test;
import com.capgemini.kabanos.common.exception.LoginException;

public interface IConnector {
	
	public boolean login(String user, String password) throws LoginException;

	public Test getTestData(String testId);
	
	public List<Test> getTestsData(List<String> testIdList);
}
