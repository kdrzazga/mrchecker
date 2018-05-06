package com.capgemini.ntc.webapi.core.sso.session;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

import io.restassured.http.Cookies;

public class CookieConverter {
	
	public static List<io.restassured.http.Cookie> restAssuredCookie = new ArrayList<>();
	
	public static List<io.restassured.http.Cookie> generateRestAssuredCookiesList(CookieStore cookieStore) {
		
		for (Cookie cookie : cookieStore.getCookies()) {
			restAssuredCookie.add(new io.restassured.http.Cookie.Builder(cookie.getName(), cookie.getValue()).build());
		}
		
		return restAssuredCookie;
	}
	
	public static Cookies generateRestAssuredCookies(CookieStore cookieStore) {
		return new Cookies(generateRestAssuredCookiesList(cookieStore));
	}
	
}
