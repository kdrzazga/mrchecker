package com.capgemini.kabanos.common.domain;

public class Project {
	private long id;
	private String name;
	private String description;
	private String testFramework;
	
	public Project() {}
	
	public Project(String name, String testFramework) {
		this.name = name;
		this.testFramework = testFramework;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTestFramework() {
		return testFramework;
	}
	public void setTestFramework(String testFramework) {
		this.testFramework = testFramework;
	}
}
