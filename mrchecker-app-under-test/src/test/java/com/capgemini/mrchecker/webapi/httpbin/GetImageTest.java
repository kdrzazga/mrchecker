package com.capgemini.mrchecker.webapi.httpbin;

import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import com.capgemini.mrchecker.test.core.logger.BFLogger;
import com.capgemini.mrchecker.webapi.pages.httbin.GetImagePage;

import io.restassured.response.Response;

public class GetImageTest extends com.capgemini.mrchecker.webapi.BasePageWebApiTest {
	
	private GetImagePage getImagePage;
	
	@Test
	public void sendSimpleGETQuery() {
		getImagePage = new GetImagePage();
		
		BFLogger.logInfo("Step 1 - Sending GET query to " + GetImagePage.JPEG_ENDPOINT);
		Response response = getImagePage.getJpegImage();
		
		BFLogger.logInfo("Step 2 - Validate response status code and content type: ");
		response.then()
				.statusCode(200)
				.contentType("image/jpeg")
				.body("title", equalTo("My Title"));
	}
	
}
