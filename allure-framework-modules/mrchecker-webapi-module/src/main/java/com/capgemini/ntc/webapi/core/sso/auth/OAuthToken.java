package com.capgemini.ntc.webapi.core.sso.auth;

public class OAuthToken {
	
	private String	tokenType;
	private String	token;
	
	public OAuthToken(String tokenType, String token) {
		this.tokenType = tokenType;
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getTokenType() {
		return tokenType;
	}
	
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	
	public String getHeaderValue() {
		return tokenType + " " + token;
	}
}
