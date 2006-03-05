package org.jbookreader;

import junit.framework.TestSuite;

import org.jbookreader.book.bom.AbstractContainerNodeTest;
import org.jbookreader.book.bom.InlineImageNodeTest;
import org.jbookreader.book.bom.StringNodeTest;

/**
 * This <code>TestSuite</code> calls all other <code>TestSuite</code>
 * in this project. 
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class AllTest extends TestSuite {
	/**
	 * This constructs the <code>AllTest</code> by adding all testsuites.
	 */
	public AllTest() {
		addTestSuite(StringNodeTest.class);
		addTestSuite(AbstractContainerNodeTest.class);
		addTestSuite(InlineImageNodeTest.class);
	}
	
	/**
	 * This is an empty method just for the junit startup
	 */
	public void testDummy() {/*empty*/}
}
