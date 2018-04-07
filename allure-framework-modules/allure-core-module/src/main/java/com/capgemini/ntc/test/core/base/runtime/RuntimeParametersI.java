package com.capgemini.ntc.test.core.base.runtime;

/**
 * @author LUSTEFAN
 */
public interface RuntimeParametersI {
	
	/**
	 * @return value of parameter
	 */
    String getValue();
	
	/**
	 * Read one more time Runtime parameters
	 */
    void refreshParameterValue();
	
}
