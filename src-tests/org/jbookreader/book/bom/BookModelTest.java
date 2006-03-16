package org.jbookreader.book.bom;

import java.io.IOException;

import junit.framework.TestCase;

import org.jbookreader.book.bom.impl.Book;
import org.jbookreader.util.ModelDumper;

/**
 * A very simple demonstration for the book model.
 * {@link org.jbookreader.book.parser.FB2Parser} was written using this demo.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 * @see org.jbookreader.book.bom.impl.Book 
 * @see org.jbookreader.book.bom 
 */
public class BookModelTest extends TestCase {

	/**
	 * The book.
	 */
	private IBook myBook;
	/**
	 * Current section.
	 */
	private ISectioningNode mySection;
	/**
	 * Current container.
	 */
	private IContainerNode myContainer;

	/**
	 * Create a very simple book tree at <code>this.myBook</code>
	 *
	 */
	@Override
	public void setUp() {
		// FixtionBook
		{
			this.myBook = new Book();
		}
		
		// body
		{
			ISectioningNode node  = this.myBook.newBody("body", null);

			this.mySection = node;
			this.myContainer = node;
		}
		
		// title
		{
			IContainerNode node = this.mySection.newTitle("title");
			this.myContainer = node;
		}
		
		// p
		{
			IContainerNode node = this.myContainer.newContainerNode("p");
			this.myContainer = node;
		}
		
		// #text
		{
			this.myContainer.newTextNode("Title");
		}
		
		// /p
		{
			this.myContainer = this.myContainer.getParentNode();
		}
		
		// /title
		{
			this.myContainer = this.myContainer.getParentNode();
		}
		
		// section
		{
			ISectioningNode node = this.mySection.newSectioningNode("section");
			
			this.mySection = node;

			this.myContainer = node;
		}
		// title
		{
			IContainerNode node = this.mySection.newTitle("title");

			this.myContainer = node;
		}
		
		// p
		{
			IContainerNode node = this.myContainer.newContainerNode("p");

			this.myContainer = node;
		}
		
		// #text
		{
			this.myContainer.newTextNode("Section title.");
		}
		
		// /p
		{
			this.myContainer = this.myContainer.getParentNode();
		}
		
		// /title
		{
			this.myContainer = this.myContainer.getParentNode();
		}
		
		// p
		{
			IContainerNode node = this.myContainer.newContainerNode("p");

			this.myContainer = node;
		}
		
		// #text
		{
			this.myContainer.newTextNode("Section text ");
		}
		
		// emphasis
		{
			IContainerNode node = this.myContainer.newContainerNode("emphasis");

			this.myContainer = node;
		}
		
		// #text
		{
			this.myContainer.newTextNode("in italic");
		}
		
		// /emphasis
		{
			this.myContainer = this.myContainer.getParentNode();
		}
		
		// #text
		{
			this.myContainer.newTextNode(".");
		}
		
		// /p
		{
			this.myContainer = this.myContainer.getParentNode();
		}
		
		// /section
		{
			this.myContainer = this.myContainer.getParentNode();
			
			this.mySection = this.mySection.getParentSection();
		}
		
		// /body
		{
			this.myContainer = this.myContainer.getParentNode();
			
			this.mySection = this.mySection.getParentSection();
		}
		
		// /FictionBook
		{
			// empty
		}
	}
	
	/**
	 * This tests if setUp() finished as expected.
	 * @throws IOException 
	 */
	public void testSetUp() throws IOException {
		assertNotNull(this.myBook);
		assertNull(this.mySection);
		assertNull(this.myContainer);
		
		ModelDumper.testBOM(this.myBook, "BOM", "BOM.xml");
	}
	
	/**
	 * This tests implementation of 'org.jbookreader.book.bom.IBody.newBody()'
	 */
	public void testNewBody() {
		ISectioningNode body = this.myBook.getMainBody();
		assertEquals("body", body.getTagName());
		assertEquals(this.myBook, body.getBook());
		assertNull(body.getParentNode());
		assertNull(body.getParentSection());
	}
	
	/**
	 * This tests implementation of 'org.jbookreader.book.bom.ISectioningNode.newSectioningNode()'
	 */
	public void testNewSection() {
		IContainerNode pnode = this.myBook.getMainBody();
		INode node = pnode.getChildNodes().iterator().next();
		
		assertEquals("section", node.getTagName());
		
		assertTrue(node.isContainer());
		IContainerNode cnode = (IContainerNode)node;
		assertTrue(cnode.isSectioningNode());
		ISectioningNode snode = (ISectioningNode)cnode;
		
		assertEquals(this.myBook, snode.getBook());
		assertEquals(pnode, snode.getParentNode());
		assertEquals(pnode, snode.getParentSection());
	}

	/**
	 * This tests implementation of 'org.jbookreader.book.bom.ISectioningNode.newTitle()'
	 */
	public void testNewTitle() {
		ISectioningNode pnode = this.myBook.getMainBody();
		
		assertNotNull(pnode.getTitle());
		INode node = pnode.getChildNodes().iterator().next();
		
		assertTrue(node.isContainer());
		IContainerNode cnode = (IContainerNode)node;
		assertTrue(cnode.isSectioningNode());
		ISectioningNode snode = (ISectioningNode)cnode;

		assertNotNull(snode.getTitle());
		
		IContainerNode title = snode.getTitle();
		assertEquals("title", title.getTagName());
		assertEquals(this.myBook,title.getBook());
		assertEquals(snode,title.getParentNode());
		assertFalse(snode.getChildNodes().contains(title));
	}

	/**
	 * This tests implementation of 'org.jbookreader.book.bom.ISectioningNode.newEpigraph()'
	 */
	public void testNewEpigraph() {
		ISectioningNode pnode = this.myBook.getMainBody();
		
		IContainerNode epigraph = pnode.newEpigraph("epigraph");
		assertEquals("epigraph", epigraph.getTagName());
		assertFalse(pnode.getEpigraph().isEmpty());
		IContainerNode enode = pnode.getEpigraph().iterator().next();
		assertEquals(epigraph, enode);

		assertEquals(this.myBook,enode.getBook());
		assertEquals(pnode,enode.getParentNode());
		assertFalse(pnode.getChildNodes().contains(enode));
	}

	/**
	 * This tests implementation of 'org.jbookreader.book.bom.ISectioningNode.newAnnotation()'
	 */
	public void testNewAnnotation() {
		ISectioningNode pnode = this.myBook.getMainBody();
		
		IContainerNode annotation = pnode.newAnnotation("annotation");
		assertEquals("annotation", annotation.getTagName());
		assertEquals(annotation, pnode.getAnnotation());

		assertEquals(this.myBook,annotation.getBook());
		assertEquals(pnode,annotation.getParentNode());
		assertFalse(pnode.getChildNodes().contains(annotation));
	}

	/**
	 * This tests implementation of 'org.jbookreader.book.bom.ISectioningNode.newImage()'
	 * FIXME: add more tests after finishing work with images!
	 */
	public void testNewImage() {
		ISectioningNode pnode = this.myBook.getMainBody();
		
		IImageNode image = pnode.newImage("image");
		assertEquals("image", image.getTagName());
		assertEquals(image, pnode.getImage());
		assertFalse(image.isContainer());

		assertEquals(this.myBook,image.getBook());
		assertEquals(pnode,image.getParentNode());
		assertFalse(pnode.getChildNodes().contains(image));
	}

	/**
	 * This tests implementation of 'org.jbookreader.book.bom.IContainerNode.newContainerNode()'
	 */
	public void testNewContainerNode() {
		IContainerNode bnode = this.myBook.getMainBody();
		
		INode childnode = bnode.getChildNodes().iterator().next();
		
		assertTrue(childnode.isContainer());
		IContainerNode pnode = (IContainerNode)childnode;

		childnode = pnode.getChildNodes().iterator().next();
		
		assertEquals("p", childnode.getTagName());
		
		assertTrue(childnode.isContainer());
//		IContainerNode node = (IContainerNode)childnode;
		
		assertEquals(this.myBook, childnode.getBook());
		assertEquals(pnode, childnode.getParentNode());
	}
	
	/**
	 * This tests implementation of 'org.jbookreader.book.bom.IContainerNode.newTextNode()'
	 */
	public void testNewTextNode() {
		IContainerNode bnode = this.myBook.getMainBody();
		
		INode childnode = bnode.getChildNodes().iterator().next();
		
		IContainerNode pnode = (IContainerNode)childnode;

		childnode = pnode.getChildNodes().iterator().next();
		IContainerNode cnode = (IContainerNode)childnode;
		
		INode node = cnode.newTextNode("test text");
		
		assertTrue(cnode.getChildNodes().contains(node));
		assertEquals("#text", node.getTagName());
		assertFalse(node.isContainer());
		assertEquals("test text", node.getText());
	}
}
