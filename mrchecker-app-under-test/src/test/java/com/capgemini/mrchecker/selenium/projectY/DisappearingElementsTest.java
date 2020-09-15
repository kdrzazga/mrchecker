package com.capgemini.mrchecker.selenium.projectY;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.capgemini.mrchecker.core.groupTestCases.testSuites.tags.TestsChrome;
import com.capgemini.mrchecker.core.groupTestCases.testSuites.tags.TestsFirefox;
import com.capgemini.mrchecker.core.groupTestCases.testSuites.tags.TestsIE;
import com.capgemini.mrchecker.core.groupTestCases.testSuites.tags.TestsSelenium;
import com.capgemini.mrchecker.selenium.pages.projectY.DisappearingElementsPage;

@Category({ TestsSelenium.class, TestsChrome.class, TestsFirefox.class, TestsIE.class })
public class DisappearingElementsTest extends TheInternetBaseTest {
	
	private static DisappearingElementsPage disappearingElementsPage;
	
	private static int			numberOfMenuButtons			= 0;
	private static final int	totalNumberOfMenuButtons	= 5;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		disappearingElementsPage = shouldTheInternetPageBeOpened().clickDisappearingElementsLink();
		
		logStep("Verify if Disappearing Elements page is opened");
		assertTrue("Unable to open Disappearing Elements page", disappearingElementsPage.isLoaded());
		
		logStep("Verify if menu button elements are visible");
		numberOfMenuButtons = disappearingElementsPage.getNumberOfMenuButtons();
		assertTrue("Unable to display menu", numberOfMenuButtons > 0);
	}
	
	@Test
	public void shouldMenuButtonElementAppearAndDisappearAfterRefreshTest() {
		logStep("Click refresh button until menu button appears");
		disappearingElementsPage.refreshPageUntilWebElementAppears(true);
		
		logStep("Verify if menu button element appeared");
		assertNotNull("Unable to disappear menu button element", disappearingElementsPage.getGalleryMenuElement());
		assertEquals("The number of button elements after refresh is incorrect", totalNumberOfMenuButtons, disappearingElementsPage.getNumberOfMenuButtons());
		
		logStep("Click refresh button until menu button disappears");
		disappearingElementsPage.refreshPageUntilWebElementAppears(false);
		
		logStep("Verify if menu button element disappeared");
		assertNull("Unable to appear menu button element", disappearingElementsPage.getGalleryMenuElement());
		assertTrue("The number of button elements after refresh is incorrect", totalNumberOfMenuButtons > disappearingElementsPage.getNumberOfMenuButtons());
	}
	
}
