package com.capgemini.kabanos.gatherer.extractor;

import java.util.List;

import com.capgemini.kabanos.common.domain.Preposition;
import com.capgemini.kabanos.common.domain.Test;
import com.capgemini.kabanos.common.domain.TestFile;

public interface IExtractor {
	public TestFile extractFunctionsFromTestFile(String path);
	
	public List<Preposition> gatherKnowledgeFromTest(Test t);
}
 