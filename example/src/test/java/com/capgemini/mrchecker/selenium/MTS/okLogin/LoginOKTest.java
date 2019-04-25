package com.capgemini.mrchecker.selenium.MTS.okLogin;


import static org.junit.Assert.assertTrue;

import org.junit.Test;


import com.capgemini.mrchecker.test.core.BaseTest;

public class LoginOKTest extends BaseTest {
	private static String		userName	= "user1";
	private static String		password	= "password";
	private static HomePage		homePage;
	private static LoginPage	loginPage;
	
	@Override
	public void setUp() {
		homePage = new HomePage();
	}
	
	@Test
	public void Login_OK() {
		assertTrue("Site title: " + homePage.getActualPageTitle(),
				homePage.getActualPageTitle()
						.equals(homePage.pageTitle()));
		loginPage = homePage.clickLoginButton();
		loginPage.setUserName(userName);
		loginPage.setPassword(password);
		loginPage.clickSubmitLoginButton();
		assertTrue("Error message is not visible", homePage.isLoginErrorTextVisible());
	}
	
	@Override
	public void tearDown() {
		homePage.cleanCoockies();
		homePage.closeBrowser();
	}
}
