package org.jbookreader;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;

import junit.framework.Assert;

import org.jbookreader.book.bom.IBook;
import org.jbookreader.util.ModelDumper;

/**
 * This class contains some utility functions for tests.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class TestUtil {
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
			
			if (s1 == null && s2 == null)
				return null;
			
			if (s1 == null)
				return "Expected EOF, but got " + s2;
			
			if (s2 == null)
				return "Expected '" + s1 + "' but got EOF";
			
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
	private static void assertFileEqualsStream(File expectedFile, File testingFile) {
		try {
			Reader r1 = new BufferedReader(new InputStreamReader(new FileInputStream(expectedFile), "UTF-8"));
			Reader r2 = new BufferedReader(new InputStreamReader(new FileInputStream(testingFile), "UTF-8"));
			String s = compareReaders(r1, r2);
			
			r1.close();
			r2.close();
		
			if (s != null)
				Assert.fail(s);
		} catch (IOException ioe) {
			throw new RuntimeException("Got IOException during file comparation", ioe);
		}
	}

	/**
	 * Compares BOM with representation in the file <code>expected</code>.
	 * @param book the book to compare
	 * @param expected expected dump file
	 * @throws IOException in case of I/O error
	 */
	public static void compareBOM(IBook book, File expected) throws IOException {
		File tempFile = File.createTempFile(expected.getName() + '.', ".test", TestConfig.getTempDir());
		
		PrintWriter pwr = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(tempFile))));
		
		ModelDumper.dumpBOM(pwr, book);
		
		pwr.close();
		
		assertFileEqualsStream(expected, tempFile);
		
		tempFile.delete();
	}

	/**
	 * Returns the test file with specified <code>id</code> for <code>module</code>.
	 * @param module the module for which to get test
	 * @param id the id of the test
	 * @return the test file.
	 */
	public static File getTestFile(String module, String id) {
		return new File(new File(TestConfig.getTestsDir(), module), id);
	}

	/**
	 * Returns the file with expected result of test for <code>module</code>
	 * specified by<code>id</code> .
	 * @param module the module for which to get expected result file
	 * @param id the id of the test
	 * @return the expected result file.
	 */
	public static File getExpectedFile(String module, String id) {
		return new File(new File(TestConfig.getExpectedDir(), module), id);
	}
}
