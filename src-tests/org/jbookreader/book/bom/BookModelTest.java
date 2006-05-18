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
package org.jbookreader.book.bom;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import junit.framework.TestCase;

import org.jbookreader.TestConfig;
import org.jbookreader.book.bom.impl.Book;
import org.jbookreader.util.ModelDumper;
import org.lumag.filetest.FileTestUtil;

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
			IContainerNode node  = this.myBook.newBody("body", null);

			this.myContainer = node;
		}
		
		// title
		{
			IContainerNode node = this.myContainer.newTitle("title");
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
			IContainerNode node = this.myContainer.newContainerNode("section");
			
			this.myContainer = node;
		}
		// title
		{
			IContainerNode node = this.myContainer.newTitle("title");

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
		}
		
		// /body
		{
			this.myContainer = this.myContainer.getParentNode();
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
		assertNull(this.myContainer);
		
		File expected = FileTestUtil.getExpectedFile(new TestConfig(),  "BOM", "BOM.xml");
		File tempFile = FileTestUtil.getTempFile(new TestConfig(), "BOM", "BOM.xml");
		
		PrintWriter pwr = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(tempFile))));
		
		ModelDumper.dumpBOM(pwr, this.myBook);
		
		pwr.close();
		
		FileTestUtil.assertFileEqualsStream(expected, tempFile);
		
		tempFile.delete();
	}
	
	/**
	 * This tests implementation of 'org.jbookreader.book.bom.IBody.newBody()'
	 */
	public void testNewBody() {
		IContainerNode body = this.myBook.getMainBody();
		assertEquals("body", body.getTagName());
		assertEquals(this.myBook, body.getBook());
		assertNull(body.getParentNode());
	}
	
	/**
	 * This tests implementation of 'org.jbookreader.book.bom.IContainerNode.newTitle()'
	 */
	@SuppressWarnings("null")
	public void testNewTitle() {
		IContainerNode pnode = this.myBook.getMainBody();
		
		assertNotNull(pnode.getTitle());

		INode node = null;
		for (INode temp: pnode.getChildNodes()) {
			if (temp.getTagName().equals("section")) {
				node = temp;
				break;
			}
		}
		
		assertNotNull("Can't find 'section' node", node);
		
		IContainerNode cnode = (IContainerNode) node;
		assertNotNull(cnode);

		assertNotNull(cnode.getTitle());
		
		IContainerNode title = cnode.getTitle();
		assertEquals("title", title.getTagName());
		assertEquals(this.myBook,title.getBook());
		assertEquals(cnode,title.getParentNode());
		assertTrue(cnode.getChildNodes().contains(title));
	}

	/**
	 * This tests implementation of 'org.jbookreader.book.bom.IContainerNode.newContainerNode()'
	 */
	public void testNewContainerNode() {
		IContainerNode bnode = this.myBook.getMainBody();
		
		INode childnode = bnode.getChildNodes().iterator().next();
		
		IContainerNode pnode = (IContainerNode) childnode;
		assertNotNull(pnode);

		childnode = pnode.getChildNodes().iterator().next();
		
		assertEquals("p", childnode.getTagName());
		
		assertNotNull(childnode);
//		IContainerNode node = childnode;
		
		assertEquals(this.myBook, childnode.getBook());
		assertEquals(pnode, childnode.getParentNode());
	}
	
	/**
	 * This tests implementation of 'org.jbookreader.book.bom.IContainerNode.newTextNode()'
	 */
	public void testNewTextNode() {
		IContainerNode bnode = this.myBook.getMainBody();
		
		INode childnode = bnode.getChildNodes().iterator().next();
		
		IContainerNode pnode = (IContainerNode) childnode;

		childnode = pnode.getChildNodes().iterator().next();
		IContainerNode cnode = (IContainerNode) childnode;
		
		INode node = cnode.newTextNode("test text");
		
		assertTrue(cnode.getChildNodes().contains(node));
		assertEquals("#text", node.getTagName());
		assertFalse(node instanceof IContainerNode);
		assertEquals("test text", node.getText());
	}
}
