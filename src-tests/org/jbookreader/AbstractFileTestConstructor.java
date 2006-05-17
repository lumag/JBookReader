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
