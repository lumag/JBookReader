package org.jbookreader;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jbookreader.book.bom.InlineImageNodeTest;
import org.jbookreader.book.bom.impl.ContainerNodeTest;
import org.jbookreader.book.bom.impl.StringNodeTest;

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

		test.addTestSuite(StringNodeTest.class);
		test.addTestSuite(ContainerNodeTest.class);
		test.addTestSuite(InlineImageNodeTest.class);

		return test;
	}
}
