package com.capgemini.kabanos.gatherer.extractor;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Test;

public class JUnitExtractorTest {
	
	private JUnitExtractor junitExtractor = new JUnitExtractor(new Properties());
	
	@Test
    public void extractLoggerText_1() {
		String multipartLogger = "Logger.log(\"check if elEment is displayed\" +\"second line \" + someVariable + \" second line second part\" +\"third line\");";

		String prefix = "Logger.log(\"";
		String suffix = "\");";
		String stepRegex = "";
		
		String expecterResult = "check if elEment is displayedsecond line ____ second line second partthird line";
		
		assertEquals("Wrong extracted step description", 
				this.junitExtractor.extractLoggerText(multipartLogger,prefix, suffix, stepRegex), 
				expecterResult);
    }
	
	@Test
    public void extractLoggerText_2() {
		String multipartLogger = "Logger.log(\"CHECK if elEment is displayed\");";

		String prefix = "Logger.log(\"";
		String suffix = "\");";
		String stepRegex = "";
		
		String expecterResult = "CHECK if elEment is displayed";

		
		assertEquals("Wrong extracted step description", 
				this.junitExtractor.extractLoggerText(multipartLogger,prefix, suffix, stepRegex), 
				expecterResult);
    }
	
	@Test
    public void extractLoggerText_3() {
		String multipartLogger = "Logger.log(\"STEP 123. CHECK if elEment is displayed\");";

		String prefix = "Logger.log(\"";
		String suffix = "\");";
		String stepRegex = "STEP \\d+.";
		
		String expecterResult = "CHECK if elEment is displayed";
		
		assertEquals("Wrong extracted step description", 
				this.junitExtractor.extractLoggerText(multipartLogger,prefix, suffix, stepRegex), 
				expecterResult);
    }
	
	@Test
    public void extractLoggerText_4() {
		String multipartLogger = "justLog STEP 123. CHECK if elEment is displayed;";

		String prefix = "justLog";
		String suffix = ";";
		String stepRegex = "STEP \\d+(.)";

		String expecterResult = "CHECK if elEment is displayed";
		
		assertEquals("Wrong extracted step description", 
				this.junitExtractor.extractLoggerText(multipartLogger,prefix, suffix, stepRegex), 
				expecterResult);
    }
}
