package com.capgemini.kabanos.common.domain;

import java.util.ArrayList;
import java.util.List;

public class Test {
	
	private String name;
	//steps from the test source eg, JIRA, QC
	private List<Step> steps = new ArrayList<Step>();
	
	//all test implementation lines- this is used when test files are parsed
	private List<String> implementationLines = new ArrayList<String>();
	
	public Test() 
	{}
	
	public Test(String name, List<Step> steps) {
		this.name = name;
		this.steps = steps;
	}
	
	public void addLine(String step) {
		this.implementationLines.add(step);
	}
	
	public List<String> getLines() {
		return this.implementationLines;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Step> getSteps() {
		return steps;
	}
	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	public void addStep(Step step) {
		this.steps.add(step);
	}
}
