package com.capgemini.kabanos.gatherer.extractor;

import java.io.IOException;

import com.capgemini.kabanos.common.domain.TestFile;

public interface IExtractor {
	public TestFile extractFunctionsFromTestFile(String path) throws IOException;
}
 