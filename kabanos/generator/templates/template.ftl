package ADD.PACKAGE.NAME;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


public class SomeClassName {

	//Test name: ${testName}
	@Test
	public void functionName() {

	    <#list testSteps as step>
		Logger.log("STEP ${step_index + 1}. ${step.description}");
		<#list step.implementations as impl>
		// ${impl.occurrences} occurences
		<#list impl.separatedLines as line>
		// ${line}
		</#list>
		//____________________________
		</#list>
	    
	    
		</#list>
	}
}