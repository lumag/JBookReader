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

import junit.framework.TestCase;

/**
 * This is an abstract file test case. To use it, implement the
 * {@link FileTestCase#generateOutput(File, File)} method.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public abstract class FileTestCase extends TestCase {
	/**
	 * The file with the test
	 */
	private File myFile;
	/**
	 * The module which we test.
	 */
	private String myModule;
	
	/**
	 * Sets the file with test.
	 * @param file the file with test
	 */
	public void setFile(File file) {
		this.myFile = file;
	}
	
	/**
	 * Sets the module we test.
	 * @param module the module we test.
	 */
	public void setModule(String module) {
		this.myModule = module;
	}
	
	/**
	 * This tests one test/expected file pair. If the test fails, the file with generated result
	 * is left in the {@link TestConfig#getTempDir()} directory.
	 * @throws Exception if anything goes wrong
	 */
	public void testFile() throws Exception {
		File expected =  TestUtil.getExpectedFile(this.myModule, this.myFile.getName());
		File tempFile = File.createTempFile(this.myModule + ".",  "." + expected.getName(), TestConfig.getTempDir());
		
		generateOutput(this.myFile, tempFile);
		
		TestUtil.assertFileEqualsStream(expected, tempFile);
		
		tempFile.delete();

	}

	/**
	 * Processes input file into output file.
	 * @param inFile the input file (the one with test).
	 * @param outFile the output file (here goes generated result).
	 * @throws Exception if anything goes wrong.
	 */
	protected abstract void generateOutput(File inFile, File outFile) throws Exception;
}