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
}
