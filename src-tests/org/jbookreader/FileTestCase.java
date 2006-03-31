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
		File tempFile = File.createTempFile(expected.getName() + '.', ".test", TestConfig.getTempDir());
		
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