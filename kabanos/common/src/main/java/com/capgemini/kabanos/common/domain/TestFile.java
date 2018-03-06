package com.capgemini.kabanos.common.domain;

import java.util.ArrayList;
import java.util.List;

public class TestFile {
	
	private String filePath;
	private List<Test> tests = new ArrayList<Test>();

	public void addTest(Test test) {
		this.tests.add(test);
	}
	
	public List<Test> getTests() {
		return tests;
	}

	public void setTests(List<Test> tests) {
		this.tests = tests;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}	
}
