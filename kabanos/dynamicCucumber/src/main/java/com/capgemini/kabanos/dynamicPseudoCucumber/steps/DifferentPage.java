package com.capgemini.kabanos.dynamicPseudoCucumber.steps;

import com.capgemini.kabanos.dynamicPseudoCucumber.annotations.Step;

public class DifferentPage {

	@Step("Go back to main facebook page")
	public void forstStep() {
		System.out.println("ececuting logout from go back to main facebook page step");
		System.out.println("....");
	}
	
	
	@Step("Login as (.*)")
	public void regex1() {
		System.out.println("ececuting open login as REGEX step");
		System.out.println("....");
	}
	
	

	@Step("Delete first messagem and count total messages")
	public void lastImple() {
		System.out.println("ececuting open login as Delete first messagem and count total messages step");
		System.out.println("....");
	}
	
}