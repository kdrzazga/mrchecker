package com.capgemini.kabanos.generator.mock;


import com.capgemini.kabanos.common.domain.Step;
import com.capgemini.kabanos.common.domain.Test;

public class MockJiraTestGenerator {

	
	public static Test generateTest() {
		
		Test result = new Test();
		
		result.setName("MOCK TEST 1");
		
		result.addStep(new Step("Go to www.facebook.com"));
		result.addStep(new Step("Login as JohnDoe"));
		result.addStep(new Step("Open messages"));
		result.addStep(new Step("Press this button again"));
		result.addStep(new Step("Log out from facebook"));
		
		return result;
	}
}
