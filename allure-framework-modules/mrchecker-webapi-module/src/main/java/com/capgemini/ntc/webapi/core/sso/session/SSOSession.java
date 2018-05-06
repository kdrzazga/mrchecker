package com.capgemini.ntc.webapi.core.sso.session;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import io.restassured.http.Cookies;

public class SSOSession {
	
	public static final String	ssoLogin	= "";
	public static final String	ssoPasword	= "";
	public static final String	hostName	= "smcred-dev.fmr.com";
	public static final int		port		= 80;
	public static final String	SCHEME		= "http";
	public static final String	URL			= "/siteminderagent/ntlm/creds.ntc?CHALLENGE=&SMAGENTNAME=VNtrKscLMdbGxniEdHNdFfqa1ymLSLUmZVHZUjN593SAlWbIHhcLQt7ACCgGYLeO&TARGET=-SM-https%3a%2f%2fecforms--sit.fmr.com%3a20013%2fECForms%2fcompliance";
	
	public static CloseableHttpClient getHttpClientSSL()
					throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
			@Override
			public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				return true;
			}
		})
						.build();
		
		SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
						new String[] { "TLSv1" }, null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
		CloseableHttpClient httpClient = HttpClients.custom()
						.setSSLSocketFactory(sslConnectionSocketFactory)
						.build();
		
		return httpClient;
	}
	
	public static HttpClientContext getContext()
					throws IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		HttpHost target = new HttpHost(hostName, port, SCHEME);
		BasicCookieStore cookieStore = new BasicCookieStore();
		HttpClientContext context = HttpClientContext.create();
		context.setCredentialsProvider(getCredProvider());
		context.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
		HttpGet httpGet = new HttpGet(URL);
		CloseableHttpResponse response = getHttpClientSSL().execute(target, httpGet, context);
		
		response.close();
		
		return context;
		
	}
	
	private static CredentialsProvider getCredProvider() {
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, new NTCredentials(ssoLogin, ssoPasword, "", ""));
		
		return credentialsProvider;
	}
	
	public static CookieStore getApacheCookies()
					throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
		return getContext().getCookieStore();
	}
	
	public static Cookies getRestAssuredCookies()
					throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
		return CookieConverter.generateRestAssuredCookies(getApacheCookies());
	}
}
