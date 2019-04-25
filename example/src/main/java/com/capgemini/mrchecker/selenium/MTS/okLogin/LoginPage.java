package com.capgemini.mrchecker.selenium.MTS.okLogin;

import org.openqa.selenium.By;

import com.capgemini.mrchecker.selenium.core.BasePage;
import com.capgemini.mrchecker.selenium.core.newDrivers.elementType.InputTextElement;

public class LoginPage extends BasePage {
	private final static By	selectorUserNameInput		= By.cssSelector("[name= \"username\"]");
	private final static By	selectorPasswordInput		= By.cssSelector("[name= \"password\"]");
	private final static By	selectorSubmitLoginButton	= By.cssSelector("[name=\"submitLogin\"]");
	
	@Override
	public boolean isLoaded() {
		// TASK Auto-generated method stub
		return false;
	}
	
	@Override
	public void load() {
		// TASK Auto-generated method stub
	}
	
	@Override
	public String pageTitle() {
		// TASK Auto-generated method stub
		return null;
	}
	
	public void setUserName(String value) {
		InputTextElement inputTextElement = new InputTextElement(selectorUserNameInput);
		inputTextElement.clearInputText();
		inputTextElement.setInputText(value);
	}
	
	public void setPassword(String value) {
		InputTextElement inputTextElement = new InputTextElement(selectorPasswordInput);
		inputTextElement.clearInputText();
		inputTextElement.setInputText(value);
	}
	
	public void clickSubmitLoginButton() {
		getDriver().elementButton(selectorSubmitLoginButton)
				.click();
	}
}
