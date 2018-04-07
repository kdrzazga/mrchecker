package com.capgemini.ntc.test.core.base.properties;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class PropertiesCoreTest {
	
	private boolean	coreIsAnalyticsEnabled	= true;
	private boolean	coreIsEncryptionEnabled	= false;
	
	@Inject(optional = true)
	private void setIsAnalyticsEnabled(@Named("core.isAnalyticsEnabled") String status) {
<<<<<<< HEAD
		this.coreIsAnalyticsEnabled = !status.toLowerCase()
                .equals("false");
		
=======
		this.coreIsAnalyticsEnabled = status.toLowerCase()
		        .equals("false") ? false : true;
>>>>>>> remotes/origin/develop
	}
	
	@Inject(optional = true)
	private void setIsEncryptionEnabled(@Named("core.isEncryptionEnabled") String status) {
		this.coreIsEncryptionEnabled = status.toLowerCase()
		        .equals("true") ? true : false;
	}
	
	public boolean isAnalyticsEnabled() {
		return this.coreIsAnalyticsEnabled;
	}
	
	public boolean isEncryptionEnabled() {
		return coreIsEncryptionEnabled;
	}
	
}
