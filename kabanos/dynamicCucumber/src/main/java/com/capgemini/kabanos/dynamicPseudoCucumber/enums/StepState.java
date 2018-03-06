package com.capgemini.kabanos.dynamicPseudoCucumber.enums;

/*
 * a numeric code is assigned to every status
 * this value with description will be returned to the web application
 */
public enum StepState {

	NULL_OBJECT(-1, "NULL OBJECT"),
	
	
	SUCCESS(0, "step executed successfully"),					
	
	
	FAILURE(1, "step failed"),					

	//sometimes a step after a failing step can pass. steps like that can be executed
	//but they will be marked by this status instead of success
	SUCCESS_AFTER_FAILURE(2, "Step success but a previous step failed"),

	//similar to previous, but failed. If fail alter a failed step the the test execution is stopped
	FAILURE_AFTER_FAILURE(3, "Step failed but a previous step did also fail"),


	MISSING_IMPLEMENTATION(4, "Step not implemented"),


	MISSING_IMPLEMENTATION_AND_NOT_REACHABLE(5, "Step not implemented and unable to execute because a previous step failed"),

	NOT_REACHABLE(5, "step was not executed because a previous step failed");


	private int code;
	private String description;
	
	private StepState(int code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
