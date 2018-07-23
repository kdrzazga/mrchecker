package com.capgemini.mrchecker.webapi.pages.httbin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

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
	
	public String encodeFileToBase64(File file) {
		String encodedfile = null;
		try {
			FileInputStream fileInputStreamReader = new FileInputStream(file);
			byte[] bytes = new byte[(int) file.length()];
			fileInputStreamReader.read(bytes);
			encodedfile = new String(Base64.getEncoder()
					.encode(bytes), "UTF-8");
			fileInputStreamReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return encodedfile;
	}
	
	public String encodeResponseToBase64(Response response) {
		String encodedResponse = null;
		
		try {
			byte[] bytes = response.body()
					.asByteArray();
			encodedResponse = new String(Base64.getEncoder()
					.encode(bytes), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return encodedResponse;
	}
}
