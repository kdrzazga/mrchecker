package com.capgemini.ntc.webapi.pages.SSOAuthExamples;

import com.capgemini.ntc.webapi.core.BasePageWebAPI;
import com.capgemini.ntc.webapi.core.base.driver.DriverManager;

import io.restassured.response.Response;

public class CapgeminiSSOAuthPage extends BasePageWebAPI {
	
	private final static String	HOSTNAME	= "http://capgemini.com";
	private final static String	PATH		= "";
	private final static String	ENDPOINT	= HOSTNAME + PATH;
	
	public Response sendGETQuery() {
		return DriverManager.getDriverWebAPI()
						.when()
						.get(ENDPOINT);
	}
	
	@Override
	public String getEndpoint() {
		return ENDPOINT;
	}
}