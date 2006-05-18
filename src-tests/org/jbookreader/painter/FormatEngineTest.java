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
package org.jbookreader.painter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import junit.framework.Test;

import org.jbookreader.FB2FilesTestFilter;
import org.jbookreader.TestConfig;
import org.jbookreader.book.bom.IBook;
import org.jbookreader.book.parser.FB2Parser;
import org.jbookreader.formatengine.IBookPainter;
import org.jbookreader.formatengine.impl.FormatEngine;
import org.jbookreader.renderingengine.RenderingEngine;
import org.jbookreader.util.TextPainter;
import org.lumag.filetest.FileTestCase;
import org.lumag.filetest.FileTestUtil;

/**
 * This class is a test case generator for the {@link org.jbookreader.formatengine.impl.FormatEngine}.
 * The engine is tested via formatting with {@link org.jbookreader.util.TextPainter}.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class FormatEngineTest {
	
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
			return FileTestUtil.constructTestSuite(new TestConfig(), "fengine", FB2FilesTestFilter.class, FormatEngineTestCase.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
