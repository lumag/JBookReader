package org.jbookreader;

import java.io.File;

/**
 * This class represents tests configuration
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class TestsConfig {
	/**
	 * The <code>File</code> representing a directory holding all test files.
	 */
	private static final File TESTS_DIR = new File("tests", "tests");  

	/**
	 * The <code>File</code> representing a directory holding all expected result files.
	 */
	private static final File EXPECTED_DIR = new File("tests", "expected");

	/**
	 * The <code>File</code> representing a directory holding all temporary files.
	 */
	private static final File TEMP_DIR = new File("tests", "temp");

	/**
	 * Returns a <code>File</code> representing a directory holding all expected result files.
	 * @return a <code>File</code> representing a directory holding all expected result files.
	 */
	public static File getExpectedDir() {
		return EXPECTED_DIR;
	}

	/**
	 * Returns a <code>File</code> representing a directory holding all test files.
	 * @return a <code>File</code> representing a directory holding all test files.
	 */
	public static File getTestsDir() {
		return TESTS_DIR;
	}
	
	/**
	 * Returns a <code>File</code> representing a directory holding all temporary files.
	 * @return a <code>File</code> representing a directory holding all temporary files.
	 */
	public static File getTempDir() {
		return TEMP_DIR;
	}

	/**
	 * Returns the test file with specified <code>id</code> for <code>module</code>.
	 * @param module the module for which to get test
	 * @param id the id of the test
	 * @return the test file.
	 */
	public static File getTestFile(String module, String id) {
		return new File(new File(getTestsDir(), module), id);
	}

	/**
	 * Returns the file with expected result of test for <code>module</code>
	 * specified by<code>id</code> .
	 * @param module the module for which to get expected result file
	 * @param id the id of the test
	 * @return the expected result file.
	 */
	public static File getExpectedFile(String module, String id) {
		return new File(new File(getExpectedDir(), module), id);
	}

}
