package org.jbookreader.parser;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jbookreader.TestConfig;
import org.jbookreader.TestUtil;
import org.jbookreader.book.bom.IBook;
import org.jbookreader.book.parser.FB2Parser;
import org.xml.sax.SAXException;

/**
 * A testsuite for the {@link org.jbookreader.book.parser.FB2Parser} class.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class FB2ParserTest {
	
	/**
	 * This class represents one TestCase for parser (one pair of input/expected files).
	 * 
	 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
	 *
	 */
	public static class FB2ParserTestCase extends TestCase {
		/**
		 * The file with the test
		 */
		private final File myFile;

		/**
		 * This constructs new parser testcase.
		 * @param f the file with the test
		 */
		public FB2ParserTestCase(File f) {
			this.myFile = f;
			this.setName("testParser");
		}
		
		/**
		 * Tests the parser by checking if the dump of the parsed file equals
		 * to the expected result.
		 * @throws IOException in case of I/O error. 
		 * @throws SAXException if parsing fails.
		 */
		public void testParser() throws IOException, SAXException {
			IBook book = FB2Parser.parse(this.myFile);
			TestUtil.compareBOM(book, TestUtil.getExpectedFile("fb2parser", this.myFile.getName()));
		}
	}
	
	/**
	 * This is simple file filter that accepts only .fb2 and .xml files.
	 * 
	 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
	 *
	 */
	private static class FB2FilesFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			if (name.endsWith(".fb2"))
				return true;
			if (name.endsWith(".xml"))
				return true;
			return false;
		}
		
	}
	
	/**
	 * Generates testsuite containing {@link FB2ParserTestCase} for each test file.
	 * @return a test for the FB2 parser.
	 */
	public static Test suite() {
		TestSuite suite = new TestSuite();

		File fb2TestsDirectory = new File(TestConfig.getTestsDir(), "fb2parser");	
		File[] tests = fb2TestsDirectory.listFiles(new FB2FilesFilter());
		for (File f: tests) {
			suite.addTest(new FB2ParserTestCase(f));
		}
		
		return suite;
	}
}
