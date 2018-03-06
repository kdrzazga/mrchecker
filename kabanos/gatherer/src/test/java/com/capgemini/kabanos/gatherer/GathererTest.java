package com.capgemini.kabanos.gatherer;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class GathererTest {

	private Gatherer gatherer;
	
	@Before
	public void init() {
		this.gatherer = new Gatherer();
	}
	
	@Test
    public void extractLoggerText_1() {
		String multipartLogger = "Logger.log(\"check if elEment is displayed\" +\"second line\" + someVariable + \" second line second part\" +\"third line\");";

		String prefix = "Logger.log(\"";
		String suffix = "\");";
		String stepRegex = "";
		
		String expecterResult = "checkifelementisdisplayedsecondline_____secondlinesecondpartthirdline";
		
		assertEquals("Wrong extracted step description", 
				this.gatherer.extractLoggerText(multipartLogger,prefix, suffix, stepRegex), 
				expecterResult);
    }
	
	@Test
    public void extractLoggerText_2() {
		String multipartLogger = "Logger.log(\"CHECK if elEment is displayed\");";

		String prefix = "Logger.log(\"";
		String suffix = "\");";
		String stepRegex = "";
		
		String expecterResult = "checkifelementisdisplayed";
		
		assertEquals("Wrong extracted step description", 
				this.gatherer.extractLoggerText(multipartLogger,prefix, suffix, stepRegex), 
				expecterResult);
    }
	
	@Test
    public void extractLoggerText_3() {
		String multipartLogger = "Logger.log(\"STEP 123. CHECK if elEment is displayed\");";

		String prefix = "Logger.log(\"";
		String suffix = "\");";
		String stepRegex = "STEP \\d+.";
		
		String expecterResult = "checkifelementisdisplayed";
		
		assertEquals("Wrong extracted step description", 
				this.gatherer.extractLoggerText(multipartLogger,prefix, suffix, stepRegex), 
				expecterResult);
    }
	
	@Test
    public void extractLoggerText_4() {
		String multipartLogger = "justLog STEP 123. CHECK if elEment is displayed;";

		String prefix = "justLog";
		String suffix = ";";
		String stepRegex = "STEP \\d+(.)";
		
		String expecterResult = "checkifelementisdisplayed";
		
		assertEquals("Wrong extracted step description", 
				this.gatherer.extractLoggerText(multipartLogger,prefix, suffix, stepRegex), 
				expecterResult);
    }
}
