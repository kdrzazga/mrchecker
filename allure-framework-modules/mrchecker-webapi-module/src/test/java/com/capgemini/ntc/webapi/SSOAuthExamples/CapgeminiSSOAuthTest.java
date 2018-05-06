package com.capgemini.ntc.webapi.SSOAuthExamples;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.capgemini.ntc.test.core.logger.BFLogger;
import com.capgemini.ntc.webapi.BasePageWebApiTest;
import com.capgemini.ntc.webapi.pages.SSOAuthExamples.CapgeminiSSOAuthPage;

import io.restassured.response.Response;

public class CapgeminiSSOAuthTest extends BasePageWebApiTest {
	
	private CapgeminiSSOAuthPage capgeminiSSOAuthPage;
	
	@Test
	public void sendSimpleGETQuery() {
		capgeminiSSOAuthPage = new CapgeminiSSOAuthPage();
		
		BFLogger.logInfo("Step 1 - Sending GET query to " + capgeminiSSOAuthPage.getEndpoint());
		Response response = capgeminiSSOAuthPage.sendGETQuery();
		
		BFLogger.logInfo("Step 2 - Validate response status code: ");
		assertThat(response.statusCode(), is(200));
	}
	
}
