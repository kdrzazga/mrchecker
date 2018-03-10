package com.capgemini.ntc.selenium.features.webElements;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import org.junit.AfterClass;
import org.junit.Test;
import org.openqa.selenium.By;

import com.capgemini.ntc.selenium.core.BasePage;
import com.capgemini.ntc.selenium.core.enums.PageSubURLsEnum;
import com.capgemini.ntc.test.core.BaseTest;


public class MessageTest extends BaseTest {
	
	private static By buttonSubmit = By.cssSelector("button#submit");
	
	@AfterClass
	public static void tearDownAll() {
	}
	
	@Test
	public void test_1() {

		Logger.log("Go to www.facebook.com");
		Facebook.INSTANCE.openPage();

		Logger.log("Login as JohnDoe");
		Facebook.INSTANCE.login("JohnDoe", "somePswd");
		assertTrue(Facebook.INSTANCE.isOnMainPage());

		Logger.log("Open messages and count not readed");
		Facebook.INSTANCE.openMessages();
		assertEquals(MessagePage.INSTANCE.countNotReadedMessages(), 13);

		Logger.log("Click on first not readed message and count not readed messages");
		MessagePage.INSTANCE.clickOneReadedMessage(1);
		assertEquals(MessagePage.INSTANCE.countNotReadedMessages(), 13);

		Logger.log("Go back to main facebook page");
		Facebook.INSTANCE.goToMainPage();
		assertTrue(Facebook.INSTANCE.isOnMainPage());

		Logger.log("Log out from facebook");
		Facebook.INSTANCE.logOut();
	}
}
