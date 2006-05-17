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

/**
 * This class represents tests configuration.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class TestConfig {
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

}
