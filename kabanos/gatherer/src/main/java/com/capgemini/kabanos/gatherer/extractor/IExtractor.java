package com.capgemini.kabanos.gatherer.extractor;

import com.capgemini.kabanos.common.domain.TestFile;

public interface IExtractor {
	public TestFile extractFunctionsFromTestFile(String path);
}
 