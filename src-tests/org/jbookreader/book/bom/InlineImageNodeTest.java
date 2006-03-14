package org.jbookreader.book.bom;

import junit.framework.TestCase;

import org.jbookreader.book.bom.impl.ImageNode;

/**
 * This is a testsuite for the {@link org.jbookreader.book.bom.impl.ImageNode}.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class InlineImageNodeTest extends TestCase {
	
	/**
	 * The node instance used for testing.
	 */
	private ImageNode myNode;

	@Override
	protected void setUp() throws Exception {
		this.myNode = new ImageNode();
//		this.myNode.setTagName("image");
	}

	/**
	 * Test method for 'org.jbookreader.book.bom.InlineImageNode.setText(String)'
	 * and 'org.jbookreader.book.bom.InlineImageNode.getText()'
	 */
	public void testGetSetText() {
		this.myNode.setText("test");
		assertEquals("test", this.myNode.getText());
	}

	/**
	 * Test method for 'org.jbookreader.book.bom.InlineImageNode.isContainer()'
	 */
	public void testIsContainer() {
		assertFalse(this.myNode.isContainer());
	}

	/**
	 * Test method for 'org.jbookreader.book.bom.InlineImageNode.setHyperRef(String)'
	 * and 'org.jbookreader.book.bom.InlineImageNode.getHyperRef()'
	 */
	public void testGetHyperRef() {
		String href = "#image.jpg";
		this.myNode.setHyperRef(href);
		assertEquals(href, this.myNode.getHyperRef());
	}

}
