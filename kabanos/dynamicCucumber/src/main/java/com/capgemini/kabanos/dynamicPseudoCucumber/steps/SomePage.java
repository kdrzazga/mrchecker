package com.capgemini.kabanos.dynamicPseudoCucumber.steps;

import com.capgemini.kabanos.dynamicPseudoCucumber.annotations.Step;

public class SomePage {
	
	@Step("Log out from facebook")
	public void forstStep() {
		System.out.println("ececuting logout from facebook step");
		System.out.println("....");
	}
	
	@Step("Go to www.facebook.com")
	public void sef() {
		System.out.println("ececuting gotowww.facebook.com step");
		System.out.println("....");
	}
	
	@Step("Create a new message to JaneDoe")
	public void differentStep() {
		System.out.println("ececuting open messages step");
		System.out.println("....");
	}
	
	
	@Step("Open messages and count not readed")
	public void differentStedsfsp() {
		throw new RuntimeException("");
	}
	
	@Step("Open messages")
	public void differentStedsfspf() {
		throw new RuntimeException();
	}
	
		
	@Step("Click on first not readed message and count not readed messages")
	public void clickonfirstnotreadedmessageandcountnotreadedmessages() {
		System.out.println("ececuting clickonfirstnotreadedmessageandcountnotreadedmessages step");
		System.out.println("....");
	}
}