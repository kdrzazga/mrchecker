package com.capgemini.kabanos.dynamicPseudoCucumber.domain;

import com.capgemini.kabanos.dynamicPseudoCucumber.enums.StepState;

public class StepExecutionResult {
	private StepState state;
	private String message;
	
	public StepExecutionResult(StepState state, String message) {
		this.state = state;
		this.message = message;
	}
	
	public StepExecutionResult(StepState state) {
		this.state = state;
	}

	public StepState getState() {
		return state;
	}
	public void setState(StepState state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
