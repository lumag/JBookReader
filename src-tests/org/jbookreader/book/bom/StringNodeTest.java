package org.jbookreader.book.bom;


import junit.framework.TestCase;

/**
 * This is a JUnit test for {@link org.jbookreader.book.bom._TextNode}.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class StringNodeTest extends TestCase {
	/**
	 * The testing node.
	 */
	private _TextNode myNode;

	/**
	 * This sets up test case.
	 */
	@Override
	protected void setUp() throws Exception {
		this.myNode = new _TextNode();
	}

	/**
	 * Test method for 'org.jbookreader.book.bom.StringNode.isContainer()'
	 */
	public void testIsContainer() {
		assertFalse(this.myNode.isContainer());
	}

	/**
	 * Test method for 'org.jbookreader.book.bom.StringNode.getText()'
	 * and 'org.jbookreader.book.bom.StringNode.setText(String)'
	 */
	public void testText() {
		String testText = "test";
		this.myNode.setText(testText);
		assertEquals(testText, this.myNode.getText());
	}

}
