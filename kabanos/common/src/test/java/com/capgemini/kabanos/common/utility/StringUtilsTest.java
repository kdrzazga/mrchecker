package com.capgemini.kabanos.common.utility;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.capgemini.kabanos.common.utility.StringUtils;

public class StringUtilsTest {

	@Test
    public void trimArrayTest_1() {
		String[] arr = {" x ", " y", "z ", "a"};
		String[] expectedResult = {"x", "y", "z", "a"};
		
		StringUtils.trimArray(arr);
		
		assertArrayEquals(arr, expectedResult);
	}
	
	@Test
    public void trimArrayTest_2_empty() {
		String[] arr = {};
		String[] expectedResult = {};
		
		StringUtils.trimArray(arr);
		
		assertArrayEquals(arr, expectedResult);
	}
	
	@Test
    public void stringRepeat_1() {
		String template = "hello_";
		String expectedResult = "hello_hello_hello_hello_";
		assertEquals(StringUtils.repeat(template, 4), expectedResult);
	}
	
	@Test
    public void stringRepeat_2_empty() {
		String template = "";
		String expectedResult = "";
		assertEquals(StringUtils.repeat(template, 4), expectedResult);
	}
	
	@Test
    public void isStartsWithOneOf_1() {
		String line = "Start some text";
		String[] prefixes = {"Start", "middle", "end"};
		assertTrue(StringUtils.isStartsWithOneOf(line, prefixes));
	}
	
	@Test
    public void isStartsWithOneOf_2() {
		String line = "Start some text";
		String[] prefixes = {"begin", "middle", "end"};
		assertFalse(StringUtils.isStartsWithOneOf(line, prefixes));
	}
	
	@Test
    public void isStartsWithOneOf_3_emptyArray() {
		String line = "Start some text";
		String[] prefixes = {};
		assertFalse(StringUtils.isStartsWithOneOf(line, prefixes));
	}
	
	@Test
    public void isStartsWithOneOf_4_emptyArrayElement() {
		String line = "Start some text";
		String[] prefixes = {""};
		assertFalse(StringUtils.isStartsWithOneOf(line, prefixes));
	}
	
}
