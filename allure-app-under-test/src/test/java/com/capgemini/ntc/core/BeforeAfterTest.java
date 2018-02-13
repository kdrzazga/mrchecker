package com.capgemini.ntc.core;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.capgemini.ntc.test.core.BaseTest;
import com.capgemini.ntc.test.core.logger.BFLogger;

public class BeforeAfterTest extends BaseTest {
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		BFLogger.logInfo("[Step1] Login as Account Administrator");
	}
	
	@AfterAll
	public static void tearDownAfterClass() throws Exception {
		BFLogger.logInfo("[Step4] Logout");
	}
	
	@Override
	public void setUp() {
		BFLogger.logInfo("Open home page before each test");
	}
	
	@Override
	public void tearDown() {
		BFLogger.logInfo("Clean all data updated while executing each test");
	}
	
	@Test
	public void test1() {
		BFLogger.logInfo("[Step2] Filter by \"Creation Date\" - Descending");
		BFLogger.logInfo("[Step3] Set $1 for first 10 Users in column \"Invoice to pay\"");
		
	}
	
	@Test
	public void test2() {
		BFLogger.logInfo("[Step2] Filter by \"Invoice to pay\" - Ascending");
		BFLogger.logInfo("[Step3] Set $100 for first 10 Users in column \"Invoice to pay\"");
	}
	
}
