package com.capgemini.mrchecker.selenium.pages.projectY;

import com.capgemini.mrchecker.selenium.core.BasePage;
import com.capgemini.mrchecker.selenium.pages.environment.GetEnvironmentParam;
import com.capgemini.mrchecker.selenium.pages.environment.PageSubURLsProjectYEnum;
import com.capgemini.mrchecker.test.core.logger.BFLogger;

public class NewWindowPage extends BasePage {
	
	@Override
	public boolean isLoaded() {
		BFLogger.logDebug("is loaded");
		return getDriver().getCurrentUrl()
				.equals(GetEnvironmentParam.THE_INTERNET_MAIN_PAGE.getValue() + PageSubURLsProjectYEnum.NEW_WINDOW.getValue());
	}
	
	@Override
	public void load() {
		BFLogger.logDebug("load page");
		getDriver().get(GetEnvironmentParam.THE_INTERNET_MAIN_PAGE.getValue() + PageSubURLsProjectYEnum.NEW_WINDOW.getValue());
		getDriver().waitForPageLoaded();
	}
	
	@Override
	public String pageTitle() {
		return getDriver().getTitle();
	}
}
