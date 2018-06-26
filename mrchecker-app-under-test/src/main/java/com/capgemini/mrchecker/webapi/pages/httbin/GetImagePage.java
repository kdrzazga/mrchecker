package com.capgemini.mrchecker.webapi.pages.httbin;

import com.capgemini.mrchecker.webapi.core.BasePageWebAPI;
import com.capgemini.mrchecker.webapi.core.base.driver.DriverManager;
import com.capgemini.mrchecker.webapi.pages.environment.GetEnvironmentParam;

import io.restassured.response.Response;

public class GetImagePage extends BasePageWebAPI {
	
	private final static String	HOSTNAME		= GetEnvironmentParam.HTTPBIN.getValue();
	private final static String	PATH			= "/image";
	private final static String	ENDPOINT		= HOSTNAME + PATH;
	public final static String	JPEG_ENDPOINT	= ENDPOINT + "/jpeg";
	public final static String	PNG_ENDPOINT	= ENDPOINT + "/png";
	public final static String	SVG_ENDPOINT	= ENDPOINT + "/svg";
	public final static String	WEBP_ENDPOINT	= ENDPOINT + "/webp";
	
	public Response getSimpleImage() {
		return this.sendGETQuery(ENDPOINT);
	}
	
	public Response getJpegImage() {
		return this.sendGETQuery(JPEG_ENDPOINT);
	}
	
	public Response getPngImage() {
		return this.sendGETQuery(PNG_ENDPOINT);
	}
	
	public Response getSvgImage() {
		return this.sendGETQuery(SVG_ENDPOINT);
	}
	
	public Response getWebpImage() {
		return this.sendGETQuery(WEBP_ENDPOINT);
	}
	
	private Response sendGETQuery(String endpoint) {
		return DriverManager.getDriverWebAPI()
				.when()
				.get(endpoint);
	}
	
	@Override
	public String getEndpoint() {
		return ENDPOINT;
	}
}
