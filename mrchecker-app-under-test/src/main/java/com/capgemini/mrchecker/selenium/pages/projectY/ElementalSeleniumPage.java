package com.capgemini.mrchecker.selenium.pages.projectY;

import com.capgemini.mrchecker.selenium.core.BasePage;
import com.capgemini.mrchecker.test.core.logger.BFLogger;

public class ElementalSeleniumPage extends BasePage {
	
	@Override
	public boolean isLoaded() {
		return pageTitle().contains("Elemental Selenium: Receive a Free, Weekly Tip on Using Selenium like a Pro");
	}
	
	@Override
	public void load() {
		BFLogger.logDebug("load()");
	}
	
	@Override
	public String pageTitle() {
		return getDriver().getTitle();
	}
}