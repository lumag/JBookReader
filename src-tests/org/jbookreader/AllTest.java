package org.jbookreader;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jbookreader.book.bom.BookModelTest;
import org.jbookreader.parser.FB2ParserTest;

/**
 * This <code>TestSuite</code> calls all other <code>TestSuite</code>
 * in this project. 
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class AllTest  {
	/**
	 * This creates testsuite.
	 * @return all-test testsuite.
	 */
	public static Test suite () {
		TestSuite test = new TestSuite();

		test.addTestSuite(BookModelTest.class);
		test.addTest(FB2ParserTest.suite());

		return test;
	}
}
