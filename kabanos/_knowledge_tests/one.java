package com.capgemini.ntc.selenium.features.webElements;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import org.junit.AfterClass;
import org.junit.Test;
import org.openqa.selenium.By;

import com.capgemini.ntc.selenium.core.BasePage;
import com.capgemini.ntc.selenium.core.enums.PageSubURLsEnum;
import com.capgemini.ntc.test.core.BaseTest;

/**
 * Created by LKURZAJ on 03.03.2017.
 */
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

	@Test
	public void test_2() {

		Logger.log("Go to www.facebook.com");
		Facebook.INSTANCE.openPage();

		Logger.log("Login as JohnDoe");
		Facebook.INSTANCE.login("JohnDoe", "somePswd");
		assertTrue(Facebook.INSTANCE.isOnMainPage());

		Logger.log("Open messages");
		Facebook.INSTANCE.openMessages();

		Logger.log("Create a new message to JaneDoe");
		MessagePage.INSTANCE.createNewMessage();
		MessagePage.INSTANCE.setRecipient("JaneDoe");

		Logger.log("write a very long text message a send it to the recipient");
		MessagePage.INSTANCE.writeVeryLongMessage();
		MessagePage.INSTANCE.sendMessage();

		Logger.log("Go back to main facebook page");
		Facebook.INSTANCE.goToMainPage();
		assertTrue(Facebook.INSTANCE.isOnMainPage());

		Logger.log("Log out from facebook");
		Facebook.INSTANCE.logOut();
	}

	@Test
	public void test_3() {

		Logger.log("Go to www.facebook.com");
		Facebook.INSTANCE.openPage();

		Logger.log("Login as JohnDoe");
		Facebook.INSTANCE.login("JohnDoe", "somePswd");
		assertTrue(Facebook.INSTANCE.isOnMainPage());

		Logger.log("Open messages");
		Facebook.INSTANCE.openMessages();


		Logger.log("Count total message count");
		int x = MessagePage.INSTANCE.countMessages();

		Logger.log("Delete first messagem and count total messages");
		assertTrue(x > 0);
		MessagePage.INSTANCE.deleteFirstMessage();
		assertEquals(MessagePage.INSTANCE.countMessages(), x - 1);
		
		Logger.log("Go back to main facebook page");
		Facebook.INSTANCE.goToMainPage();
		assertTrue(Facebook.INSTANCE.isOnMainPage());

		Logger.log("Log out from facebook");
		Facebook.INSTANCE.logOut();
	}
	

	@Test
	public void test_4() {

		Logger.log("Go to www.facebook.com");
		Facebook.INSTANCE.openPageInADifferentWay();

		Logger.log("Login as JohnDoe");
		Facebook.INSTANCE.login("JohnDoe", "somePswd");
		assertTrue(Facebook.INSTANCE.isOnMainPage());

		Logger.log("Open messages");
		Facebook.INSTANCE.openMessages();

		Logger.log("Go to contacts");
		Facebook.INSTANCE.logOut();

		Logger.log("Log out from facebook");
		Facebook.INSTANCE.logOut();
	}


	@Override
	public void setUp() {

		/*Logger.log("check if element is displayed" + 
				"second line" + someVariable + " second line seconf part" + 
				"third line");
		assertTrue(BasePage.getDriver()
				.elementButton(ButtonTest.buttonSubmit)
				.isDisplayed());
		*/

		BasePage.getDriver()
				.get(PageSubURLsEnum.TOOLS_QA.subURL() + PageSubURLsEnum.AUTOMATION_PRACTICE_FORM.subURL());
		return;
	}
	
	@Override
	public void tearDown() {
		// TODO Auto-generated method stub
	}
}
