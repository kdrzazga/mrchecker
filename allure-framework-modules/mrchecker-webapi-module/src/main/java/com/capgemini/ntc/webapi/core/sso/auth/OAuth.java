package com.capgemini.ntc.webapi.core.sso.auth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.util.EntityUtils;

import com.capgemini.ntc.test.core.logger.BFLogger;
import com.capgemini.ntc.webapi.core.sso.session.SSOSession;

import io.restassured.path.json.JsonPath;

public class OAuth {
	
	private static final String	DEFAULT_AUTH_HOST_NAME_PROPERTY		= "default.oauth.hostName";
	private static final String	DEFAULT_CLIENT_ID_PROPERTY			= "default.oauth.clientId";
	private static final String	DEFAULT_CLIENT_USER_NAME_PROPERTY	= "default.oauth.userName";
	private static final String	DEFAULT_CLIENT_SECRET_PROPERTY		= "default.oauth.client_secret";
	private static final String	DEFAULT_CLIENT_PASSWORD_PROPERTY	= "default.oauth.password";
	
	private String	oAuthHostname;
	private String	clientId;
	private String	clientSecret;
	private String	username;
	private String	password;
	
	public OAuth(String oAuthHostname, String clientId, String username, String clientSecret, String password) {
		super();
		this.oAuthHostname = oAuthHostname;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.username = username;
		this.password = password;
	}
	
	public OAuth initAsDefault() {
		String oAuthHostname = DEFAULT_AUTH_HOST_NAME_PROPERTY;
		String clientId = DEFAULT_CLIENT_ID_PROPERTY;
		String username = DEFAULT_CLIENT_USER_NAME_PROPERTY;
		String clientSecret = DEFAULT_CLIENT_SECRET_PROPERTY;
		String password = DEFAULT_CLIENT_PASSWORD_PROPERTY;
		
		return new OAuth(oAuthHostname, clientId, clientSecret, username, password);
	}
	
	public OAuthToken authenticate() throws MalformedURLException, UnsupportedEncodingException {
		HttpHost target = new HttpHost(this.oAuthHostname);
		BasicCookieStore cookieStore = new BasicCookieStore();
		HttpClientContext context = HttpClientContext.create();
		context.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
		HttpPost httpPost = new HttpPost(this.oAuthHostname);
		HttpEntity entity = new ByteArrayEntity(prepareBody().getBytes("UTF-8"));
		URL url = new URL(this.oAuthHostname);
		
		try {
			CloseableHttpResponse response = SSOSession.getHttpClientSSL()
							.execute(target, httpPost, context);
			
			JsonPath jsonPath = new JsonPath(EntityUtils.toString(response.getEntity()));
			
			String tokenType = jsonPath.getString("token_type");
			String token = jsonPath.getString("token");
			return new OAuthToken(tokenType, token);
			
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			BFLogger.logError(e.getMessage());
		}
		
		return null;
		
	}
	
	private String prepareBody() {
		return "client_id=" + this.clientId + "&client_secret=" + this.clientSecret + "&user_name=" + this.username + "&password=" + this.password;
	}
	
	public static void main(String[] args) {
		
	}
	
}
