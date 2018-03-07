package com.capgemini.kabanos.dynamicPseudoCucumber.steps;

import com.capgemini.kabanos.dynamicPseudoCucumber.annotations.Step;

public class DifferentPage {

	@Step("Go back to main facebook page")
	public void forstStep() {
		System.out.println("ececuting logout from go back to main facebook page step");
		System.out.println("....");
	}
	
	//TODO test this
	//this should fail the build
//	@Step("Open messages")
//	public void differentStep() {
//		System.out.println("duplicate");
//		System.out.println("....");
//	}
	
	
	@Step("Login as JohnDoe")
	public void differentSte22p() {
		System.out.println("ececuting open login as john doe step");
		System.out.println("....");
	}
}