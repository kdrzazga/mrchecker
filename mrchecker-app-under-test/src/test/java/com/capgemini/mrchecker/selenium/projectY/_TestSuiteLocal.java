package com.capgemini.mrchecker.selenium.projectY;

import org.junit.runner.RunWith;

import com.capgemini.mrchecker.core.groupTestCases.testSuites.tags.TestsLocal;
import com.capgemini.mrchecker.core.groupTestCases.testSuites.tags.TestsNONParallel;
import com.googlecode.junittoolbox.ExcludeCategories;
import com.googlecode.junittoolbox.IncludeCategories;
import com.googlecode.junittoolbox.SuiteClasses;
import com.googlecode.junittoolbox.WildcardPatternSuite;

@RunWith(WildcardPatternSuite.class)
@IncludeCategories({ TestsLocal.class, TestsNONParallel.class })
@ExcludeCategories({})
@SuiteClasses({ "*Test.class" })

public class _TestSuiteLocal {
	
}
