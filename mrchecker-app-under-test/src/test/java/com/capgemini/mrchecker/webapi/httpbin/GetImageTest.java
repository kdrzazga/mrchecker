package com.capgemini.mrchecker.webapi.httpbin;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import com.capgemini.mrchecker.test.core.logger.BFLogger;
import com.capgemini.mrchecker.webapi.pages.httbin.GetImagePage;

import io.restassured.response.Response;

public class GetImageTest extends com.capgemini.mrchecker.webapi.BasePageWebApiTest {
	
	private GetImagePage		getImagePage;
	private final static String	JPEG_FILE_PATH	= "src/test/resources/images/JPG_response.jpg";
	
	@Test
	public void getJpegImage() {
		getImagePage = new GetImagePage();
		
		BFLogger.logInfo("Step 1 - Sending GET query to " + GetImagePage.JPEG_ENDPOINT);
		Response response = getImagePage.getJpegImage();
		
		BFLogger.logInfo("Step 2 - Validate response status code and content type:");
		response.then()
				.statusCode(200)
				.contentType("image/jpeg");
		
		BFLogger.logInfo("Step 3 - Validate response body:");
		File file = new File(JPEG_FILE_PATH);
		String encodedFile = getImagePage.encodeFileToBase64(file);
		String encodedReponse = getImagePage.encodeResponseToBase64(response);
		
		assertEquals(encodedFile, encodedReponse);
	}
	
}
