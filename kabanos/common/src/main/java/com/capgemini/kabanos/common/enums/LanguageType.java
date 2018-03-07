package com.capgemini.kabanos.common.enums;

public enum LanguageType {
	JAVA(
			".java", 
			"@Test",
			"@BeforeClass", "@Before", "@BeforeEach", "@BeforeAll",
			"@AfterClass", "@After",  "@AfterEach", "@AfterAll"
	)
//	, //not implemented yet
//	JAVA_SCRIPT(
//			".js", 
//			"it(\""
//	), 
//	CUCUMBER(".feature", "")
	;
	
	private String fileType;
	private String[] testPrefixes;
	LanguageType(String fileType, String ... testPrefixes) {
		this.fileType = fileType;
		this.testPrefixes = testPrefixes;
	}

	public String getFileType() {
		return fileType;
	}

	public String[] getTestPrefixes() {
		return testPrefixes;
	}
}
