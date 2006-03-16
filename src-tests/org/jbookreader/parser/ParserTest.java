package org.jbookreader.parser;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jbookreader.TestsConfig;
import org.jbookreader.book.bom.IBook;
import org.jbookreader.book.parser.FB2Parser;
import org.jbookreader.util.ModelDumper;
import org.xml.sax.SAXException;

public class ParserTest {
	
	public static class FB2ParserTestCase extends TestCase {
		private final File myFile;

		public FB2ParserTestCase(File f) {
			this.myFile = f;
			this.setName("testParser");
		}
		
		public void testParser() throws IOException, SAXException {
			IBook book = FB2Parser.parse(this.myFile);
			ModelDumper.testBOM(book, "fb2parser", this.myFile.getName());
		}
	}
	
	private static class FB2FilesFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			if (name.endsWith(".fb2"))
				return true;
			if (name.endsWith(".xml"))
				return true;
			return false;
		}
		
	}
	
	public static Test suite() {
		TestSuite suite = new TestSuite();

		File fb2TestsDirectory = new File(TestsConfig.getTestsDir(), "fb2parser");	
		File[] tests = fb2TestsDirectory.listFiles(new FB2FilesFilter());
		for (File f: tests) {
			suite.addTest(new FB2ParserTestCase(f));
		}
		
		return suite;
	}
}
