package org.lumag.filetest;

import java.io.File;

public interface ITestConfig {

	/**
	 * Returns a <code>File</code> representing a directory holding all expected result files.
	 * @return a <code>File</code> representing a directory holding all expected result files.
	 */
	public abstract File getExpectedDir();

	/**
	 * Returns a <code>File</code> representing a directory holding all test files.
	 * @return a <code>File</code> representing a directory holding all test files.
	 */
	public abstract File getTestsDir();

	/**
	 * Returns a <code>File</code> representing a directory holding all temporary files.
	 * @return a <code>File</code> representing a directory holding all temporary files.
	 */
	public abstract File getTempDir();

}