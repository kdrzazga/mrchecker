package com.capgemini.kabanos.dynamicPseudoCucumber.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StepUtilsTest {

	@Test
	public void noParams() {

		String regex = "Login as Jane Doe";
		String step = "Login as Jane Doe";

		Object[] result = StepUtils.extractParamsFromStep(step, regex);

		assertEquals(0, result.length);
	}

	@Test
	public void oneStringParamOnTheEnd() {
		String regex = "Login as (.*)";
		String step = "Login as Jane Doe";

		Object[] result = StepUtils.extractParamsFromStep(step, regex);

		assertEquals(result.length, 1);
		assertEquals("Jane Doe", result[0]);
	}

	@Test
	public void oneStringParamInTheMiddle() {
		String regex = "Login as (.*) END";
		String step = "Login as Jane Doe END";

		Object[] result = StepUtils.extractParamsFromStep(step, regex);

		assertEquals(result.length, 1);
		assertEquals("Jane Doe", result[0]);
	}

	@Test
	public void twoStringParams() {
		String regex = "Login as (.*) and open (.*) page";
		String step = "Login as Jane Doe and open message page";

		Object[] result = StepUtils.extractParamsFromStep(step, regex);

		assertEquals(result.length, 2);
		assertEquals("Jane Doe", result[0]);
		assertEquals("message", result[1]);
	}

	@Test
	public void oneStringParam_1() {
		String regex = "Login as (\\d*.*\\d?SomeString) END";
		String step = "Login as Jane DoeSomeString END";

		Object[] result = StepUtils.extractParamsFromStep(step, regex);

		assertEquals(result.length, 1);
		assertEquals("Jane DoeSomeString", result[0]);
	}
	
	@Test
	public void oneStringParam_2() {
		String regex = "Login as (ABC\\d*.*\\d?XYZ) END";
		String step = "Login as ABCJane DoeXYZ END";

		Object[] result = StepUtils.extractParamsFromStep(step, regex);

		assertEquals(result.length, 1);
		assertEquals("ABCJane DoeXYZ", result[0]);
	}
	
	@Test
	public void emptyStringParam() {
		String regex = "Login as (.*) END";
		String step = "Login as END";

		Object[] result = StepUtils.extractParamsFromStep(step, regex);

		assertEquals(result.length, 1);
		assertEquals("", result[0]);
	}
	
	@Test
	public void oneIntegerParam_1() {
		String regex = "This (\\d+) is an integer number";
		String step = "This 13 is an integer number";

		Object[] result = StepUtils.extractParamsFromStep(step, regex);

		assertEquals(result.length, 1);
		assertEquals(13, result[0]);
	}
	
	@Test
	public void oneIntegerParam_2() {
		String regex = "This (\\d+([.]\\d+)?) is an integer number";
		String step = "This 13 is an integer number";

		Object[] result = StepUtils.extractParamsFromStep(step, regex);

		assertEquals(result.length, 1);
		assertEquals(13, result[0]);
	}
	
	@Test
	public void oneDoubleParam_2() {
		String regex = "This (\\d+[.]\\d+) is an integer number";
		String step = "This 13.18 is an integer number";

		Object[] result = StepUtils.extractParamsFromStep(step, regex);

		assertEquals(result.length, 1);
		assertEquals(13.18, result[0]);
	}
	
	@Test
	public void oneNegativeDoubleParam_2() {
		String regex = "This (-?\\d+[.]\\d+) is an integer number";
		String step = "This -13.18 is an integer number";

		Object[] result = StepUtils.extractParamsFromStep(step, regex);

		assertEquals(result.length, 1);
		assertEquals(-13.18, result[0]);
	}
	
	@Test
	public void oneBooleanParam_true() {
		String regex = "This (true|false) is a boolean";
		String step = "This true is a boolean";

		Object[] result = StepUtils.extractParamsFromStep(step, regex);

		assertEquals(result.length, 1);
		assertEquals(true, result[0]);
	}
	
	@Test
	public void oneBooleanParam_false() {
		String regex = "This (true|false) is a boolean";
		String step = "This false is a boolean";

		Object[] result = StepUtils.extractParamsFromStep(step, regex);

		assertEquals(result.length, 1);
		assertEquals(false, result[0]);
	}
}
