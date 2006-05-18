/*
 * JBookReader - Java FictionBook Reader
 * Copyright (C) 2006 Dmitry Baryshkov
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *   
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *   
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the Free Software
 *   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.lumag.filetest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;

import junit.framework.Assert;
import junit.framework.TestSuite;

/**
 * This class contains some utility functions for tests.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class FileTestUtil {
	/**
	 * Line-by-line compares two streams identified by <code>Reader</code>s
	 *  <code>expected</code> and <code>test</code>.
	 * @param expected first reader representing expected file
	 * @param test file reader representing testing file
	 * @return the message containing the result or null if two streams are equal
	 * @throws IOException in case of I/O problem
	 */
	private static String compareReaders(Reader expected, Reader test) throws IOException {
		LineNumberReader lr1 = new LineNumberReader(expected);
		LineNumberReader lr2 = new LineNumberReader(test);

		while (true) {
			String s1 = lr1.readLine();
			String s2 = lr2.readLine();
			
			if (s1 == null && s2 == null) {
				return null;
			}
			
			if (s1 == null) {
				return "Expected EOF, but got " + s2;
			}
			
			if (s2 == null) {
				return "Expected '" + s1 + "' but got EOF";
			}
			
			if (!s1.equals(s2)) {
				return "Expected '" + s1 + "' but got '" + s2 + "'"; 
			}
		}
	}
	
	/**
	 * Asserts if two files are unequal.
	 * @param expectedFile expected file
	 * @param testingFile testing file
	 */
	public static void assertFileEqualsStream(File expectedFile, File testingFile) {
		try {
			Reader r1 = new BufferedReader(new InputStreamReader(new FileInputStream(expectedFile), "UTF-8"));
			Reader r2 = new BufferedReader(new InputStreamReader(new FileInputStream(testingFile), "UTF-8"));
			String s = compareReaders(r1, r2);
			
			r1.close();
			r2.close();
		
			if (s != null) {
				Assert.fail(s);
			}
		} catch (IOException ioe) {
			throw new RuntimeException("Got IOException during file comparation", ioe);
		}
	}

	/**
	 * Constructs new <code>TestSuite</code> for files testing.
	 * @param config the file tests config
	 * @param module the module which we test
	 * @param filterClass the filter class for files with tests
	 * @param fileTestClass the class with testCase.
	 * @return new <code>TestSuite</code> with file tests.
	 * @throws Exception if anything goes wrong
	 */
	public static TestSuite constructTestSuite(ITestConfig config, String module, Class<? extends FilenameFilter> filterClass, Class<? extends FileTestCase> fileTestClass) throws Exception {
		TestSuite suite = new TestSuite();

		File fb2TestsDirectory = new File(config.getTestsDir(), module);	
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
	/**
	 * Returns the test file with specified <code>id</code> for <code>module</code>.
	 * @param config test configuration
	 * @param module the module for which to get test
	 * @param id the id of the test
	 * @return the test file.
	 */
	public static File getTestFile(ITestConfig config, String module, String id) {
		return new File(new File(config.getTestsDir(), module), id);
	}

	/**
	 * Returns the file with expected result of test for <code>module</code>
	 * specified by<code>id</code> .
	 * @param config test configuration
	 * @param module the module for which to get expected result file
	 * @param id the id of the test
	 * @return the expected result file.
	 */
	public static File getExpectedFile(ITestConfig config, String module, String id) {
		return new File(new File(config.getExpectedDir(), module), id);
	}
	/**
	 * Returns the file for temp result of test for <code>module</code>
	 * specified by<code>id</code> .
	 * @param config test configuration
	 * @param module the module for which to get temp result file
	 * @param id the id of the test
	 * @return the temp result file.
	 * @throws IOException If a file could not be created
	 */
	public static File getTempFile(ITestConfig config, String module, String id) throws IOException {
		return File.createTempFile(module + ".", "." + id, config.getTempDir());
	}
}
