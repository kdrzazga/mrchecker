package com.capgemini.kabanos.dynamicPseudoCucumber.domain;

import com.capgemini.kabanos.dynamicPseudoCucumber.enums.StepState;

public class StepExecutionResult extends MethodInvokeResult{

	public StepExecutionResult(StepState state) {
		super(state);
	}
	
	public StepExecutionResult(StepState state, String message) {
		super(state, message);
	}
}
