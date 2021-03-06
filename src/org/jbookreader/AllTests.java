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

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jbookreader.book.bom.BookModelTest;
import org.jbookreader.book.parser.FB2ParserTest;
import org.jbookreader.renderingengine.RenderEngineTest;

/**
 * This <code>TestSuite</code> calls all other <code>TestSuite</code>
 * in this project. 
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class AllTests {
	/**
	 * This creates testsuite.
	 * @return all-test testsuite.
	 */
	public static Test suite () {
		TestSuite test = new TestSuite();

		test.addTestSuite(BookModelTest.class);
		test.addTest(FB2ParserTest.suite());
		test.addTest(RenderEngineTest.suite());

		return test;
	}
}
