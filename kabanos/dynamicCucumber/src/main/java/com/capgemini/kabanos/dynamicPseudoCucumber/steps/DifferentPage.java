package com.capgemini.kabanos.dynamicPseudoCucumber.steps;

import com.capgemini.kabanos.dynamicPseudoCucumber.annotations.Step;

public class DifferentPage {

	@Step("gobacktomainfacebookpage")
	public void forstStep() {
		System.out.println("ececuting logout from go back to main facebook page step");
		System.out.println("....");
	}
	
	//TODO test this
	//this should fail the build
//	@Step("openmessages")
//	public void differentStep() {
//		System.out.println("duplicate");
//		System.out.println("....");
//	}
	
	
	@Step("loginasjohndoe")
	public void differentSte22p() {
		System.out.println("ececuting open login as john doe step");
		System.out.println("....");
	}
}