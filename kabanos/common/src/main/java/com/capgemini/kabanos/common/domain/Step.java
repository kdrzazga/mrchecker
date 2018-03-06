package com.capgemini.kabanos.common.domain;

import java.util.List;

public class Step {

	private String description;
	private String expectation;

	private List<Implementation> implementations;
	
	public Step(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getExpectation() {
		return expectation;
	}
	public void setExpectation(String expectation) {
		this.expectation = expectation;
	}

	public List<Implementation> getImplementations() {
		return implementations;
	}

	public void setImplementations(List<Implementation> implementations) {
		this.implementations = implementations;
	}
}
