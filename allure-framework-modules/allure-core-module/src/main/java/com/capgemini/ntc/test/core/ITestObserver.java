package com.capgemini.ntc.test.core;

public interface ITestObserver {
	void onTestSuccess();
	
	void onTestFailure();
	
	void onTestFinish();
	
	void onTestClassFinish();
	
	void addObserver();
	
	ModuleType getModuleType();
}
