package com.capgemini.mrchecker.selenium.MTS.okLogin;

import org.openqa.selenium.By;

import com.capgemini.mrchecker.selenium.core.BasePage;
import com.capgemini.mrchecker.selenium.core.exceptions.BFElementNotFoundException;
import com.capgemini.mrchecker.test.core.logger.BFLogger;

public class HomePage extends BasePage {
	private static String	homePageTitle			= "My Thai Star";
	private static String	myThaiURL				= "http://de-mucdevondepl01:8090/restaurant";
	private final static By	selectorLoginButton		= By.cssSelector("[name =\"login\"]");
	private final static By	selectorLoginErrorText	= By.cssSelector(".cdk-overlay-pane>snack-bar-container simple-snack-bar>span");
	private static String	loginErrorText			= "Http failure response for http://de-mucdevondepl01:8090/api/login: 403 OK";
	
	@Override
	public boolean isLoaded() {
		BFLogger.logDebug("Loading search result page");
		return getDriver().getTitle()
				.equals(pageTitle());
	}
	
	@Override
	public void load() {
		BasePage.getDriver()
				.get(myThaiURL);
		getDriver().waitForPageLoaded();
	}
	
	@Override
	public String pageTitle() {
		return homePageTitle;
	}
	
	public LoginPage clickLoginButton() {
		getDriver().elementButton(selectorLoginButton)
				.click();
		return new LoginPage();
	}
	
	public boolean isLoginErrorTextVisible() {
		try {
			getDriver().waitForElementVisible(selectorLoginErrorText);
			BFLogger.logDebug(getDriver().waitForElementVisible(selectorLoginErrorText)
					.getText());
			return getDriver().elementLabel(selectorLoginErrorText)
					.getText()
					.equals(loginErrorText);
		} catch (BFElementNotFoundException e) {
			return false;
		}
	}
	
	public void cleanCoockies() {
		getDriver().manage()
				.deleteAllCookies();
	}
	
	public void closeBrowser() {
		getDriver().close();
	}
}
