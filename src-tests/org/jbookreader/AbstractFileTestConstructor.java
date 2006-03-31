package org.jbookreader;

import java.io.File;
import java.io.FilenameFilter;

import junit.framework.TestSuite;

/**
 * This is an abstract constructor for file tests.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public abstract class AbstractFileTestConstructor {
	/**
	 * Constructs new <code>TestSuite</code> for files testing.
	 * @param module the module which we test
	 * @param filterClass the filter class for files with tests
	 * @param fileTestClass the class with testCase.
	 * @return new <code>TestSuite</code> with file tests.
	 * @throws Exception if anything goes wrong
	 */
	protected static TestSuite constructTestSuite(String module, Class<? extends FilenameFilter> filterClass, Class<? extends FileTestCase> fileTestClass) throws Exception {
		TestSuite suite = new TestSuite();

		File fb2TestsDirectory = new File(TestConfig.getTestsDir(), module);	
		File[] tests = fb2TestsDirectory.listFiles(filterClass.newInstance());

		for (File f: tests) {
			FileTestCase testCase = fileTestClass.newInstance();
			testCase.setName("testFile");
			testCase.setModule(module);
			testCase.setFile(f);
			suite.addTest(testCase);
		}
		
		return suite;

	}

}
