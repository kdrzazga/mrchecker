package com.capgemini.kabanos.dynamicPseudoCucumber.steps;

import com.capgemini.kabanos.dynamicPseudoCucumber.annotations.Step;

public class SomePage {
	
	@Step("logoutfromfacebook")
	public void forstStep() {
		System.out.println("ececuting logout from facebook step");
		System.out.println("....");
	}
	
	@Step("gotowww.facebook.com")
	public void sef() {
		System.out.println("ececuting gotowww.facebook.com step");
		System.out.println("....");
	}
	
	@Step("createanewmessagetojanedoe")
	public void differentStep() {
		System.out.println("ececuting open messages step");
		System.out.println("....");
	}
	
	
	@Step("openmessagesandcountnotreaded")
	public void differentStedsfsp() {
		throw new RuntimeException("");
	}
	
	@Step("openmessages")
	public void differentStedsfspf() {
		throw new RuntimeException();
	}
	
		
	@Step("clickonfirstnotreadedmessageandcountnotreadedmessages")
	public void clickonfirstnotreadedmessageandcountnotreadedmessages() {
		System.out.println("ececuting clickonfirstnotreadedmessageandcountnotreadedmessages step");
		System.out.println("....");
	}
}