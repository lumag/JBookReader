package org.jbookreader.book.bom;

import java.util.List;

import org.jbookreader.book.stylesheet.EDisplayType;

import junit.framework.TestCase;

/**
 * This is a JUnit test case for the {@link AbstractContainerNode} class.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class AbstractContainerNodeTest extends TestCase {
	
	/**
	 * The node used for testing.
	 */
	private AbstractContainerNode myNode;
	/**
	 * The name of the testing node.
	 */
	private final static String TAG_NAME = "test-tag-name";

	@Override
	protected void setUp() throws Exception {
		this.myNode = new AbstractContainerNode() {
			public EDisplayType getDisplayType() {
				return EDisplayType.NONE;
			}

			@Override
			public boolean isSectioningNode() {
				return false;
			}
		};
	}

	/**
	 * Test method for 'org.jbookreader.book.bom.AbstractContainerNode.getText()'
	 */
	public void testGetText() {
		assertEquals("", this.myNode.getText());
	}

	/**
	 * Test method for 'org.jbookreader.book.bom.AbstractContainerNode.isContainer()'
	 */
	public void testIsContainer() {
		assertTrue(this.myNode.isContainer());
	}

	/**
	 * Test method for  'org.jbookreader.book.bom.AbstractContainerNode.getChildNodes()'
	 *  and 'org.jbookreader.book.bom.AbstractContainerNode.addChildNode(INode)'
	 */
	public void testAddChildNode() {
		assertTrue(this.myNode.getChildNodes().isEmpty());
		this.myNode.addChildNode(new _TextNode());

		List<INode> nodeList = this.myNode.getChildNodes();
		assertFalse(nodeList.isEmpty());
		assertEquals(1, nodeList.size());
		assertTrue(nodeList.get(0) instanceof _TextNode);
	}

	/**
	 * Test method for 'org.jbookreader.book.bom.AbstractContainerNode.getTagName()'
	 */
	public void testGetTagName() {
		this.myNode.setTagName(TAG_NAME);
		assertEquals(TAG_NAME, this.myNode.getTagName());
	}

}
