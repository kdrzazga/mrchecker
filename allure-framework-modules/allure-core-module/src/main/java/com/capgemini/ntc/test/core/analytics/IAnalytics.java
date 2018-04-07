package com.capgemini.ntc.test.core.analytics;

public interface IAnalytics {
	
	default void sendClassName(String packageName, String className, String description) {
	}

    default void sendClassName() {
		String packageName = Thread.currentThread()
				.getStackTrace()[2].getClassName();
		String className = Thread.currentThread()
				.getStackTrace()[2].getMethodName();
		sendClassName(packageName, className, "");
		
	}

    default void sendMethodEvent(String analitycsCategoryName, String eventName) {
	}

    default void sendMethodEvent(String analitycsCategoryName) {
		String eventName = Thread.currentThread()
				.getStackTrace()[2].getMethodName();
		sendMethodEvent(analitycsCategoryName, eventName);
	}
	
	void setInstance();
	
}
