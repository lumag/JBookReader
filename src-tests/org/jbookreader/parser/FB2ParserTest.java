package org.jbookreader.parser;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import junit.framework.Test;

import org.jbookreader.AbstractFileTestConstructor;
import org.jbookreader.FB2FilesFilter;
import org.jbookreader.FileTestCase;
import org.jbookreader.book.bom.IBook;
import org.jbookreader.book.parser.FB2Parser;
import org.jbookreader.util.ModelDumper;

/**
 * A testsuite for the {@link org.jbookreader.book.parser.FB2Parser} class.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class FB2ParserTest extends AbstractFileTestConstructor {
	
	/**
	 * This class represents one TestCase for parser (one pair of input/expected files).
	 * 
	 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
	 *
	 */
	public static class FB2ParserTestCase extends FileTestCase {
		@Override
		protected void generateOutput(File inFile, File outFile) throws Exception {
			IBook book = FB2Parser.parse(inFile);
			PrintWriter pwr = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(outFile))));
			
			ModelDumper.dumpBOM(pwr, book);
			
			pwr.close();
			
		}
	}
	
	/**
	 * Generates testsuite containing {@link FB2ParserTestCase} for each test file.
	 * @return a test for the FB2 parser.
	 */
	public static Test suite() {
		try {
			return constructTestSuite("fb2parser", FB2FilesFilter.class, FB2ParserTestCase.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
