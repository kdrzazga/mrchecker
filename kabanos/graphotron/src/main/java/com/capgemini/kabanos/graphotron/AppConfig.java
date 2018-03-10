package com.capgemini.kabanos.graphotron;

import java.util.Properties;

import com.capgemini.kabanos.common.enums.ApplicationConfigType;
import com.capgemini.kabanos.common.utility.PropertiesUtils;

public enum AppConfig {
	INSTANCE;
	
	private Properties properties;
	
	public Properties getConfig() {
		if(this.properties == null)
			this.properties = PropertiesUtils.loadProperties(ApplicationConfigType.GRAPHOTRON);
		
		return this.properties;
	}	
}
