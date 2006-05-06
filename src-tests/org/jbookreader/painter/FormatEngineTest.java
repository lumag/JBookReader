package org.jbookreader.painter;

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
import org.jbookreader.formatengine.IBookPainter;
import org.jbookreader.formatengine.impl.FormatEngine;
import org.jbookreader.renderingengine.RenderingEngine;
import org.jbookreader.util.TextPainter;

/**
 * This class is a test case generator for the {@link org.jbookreader.formatengine.impl.FormatEngine}.
 * The engine is tested via formatting with {@link org.jbookreader.util.TextPainter}.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class FormatEngineTest extends AbstractFileTestConstructor {
	
	/**
	 * This is one {@link FormatEngine} <code>TestCase</code>.
	 * 
	 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
	 *
	 */
	public static class FormatEngineTestCase extends FileTestCase {
		@Override
		protected void generateOutput(File inFile, File outFile)
				throws Exception {
			IBook book = FB2Parser.parse(inFile);
			PrintWriter pwr = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(outFile))));
			
			IBookPainter painter = new TextPainter(pwr, 80);
			
			RenderingEngine engine = new RenderingEngine(new FormatEngine());

			engine.setBook(book);
			engine.setPainter(painter);
			engine.renderPage();
			
			pwr.close();
		}

		
	}

	/**
	 * Generates testsuite containing {@link FormatEngineTestCase} for each test file.
	 * @return a test for the formatting engine.
	 */
	public static Test suite() {
		try {
			return constructTestSuite("fengine", FB2FilesFilter.class, FormatEngineTestCase.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
