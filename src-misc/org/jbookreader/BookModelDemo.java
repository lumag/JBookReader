package org.jbookreader;

import org.jbookreader.book.Book;
import org.jbookreader.book.bom.BodyNode;
import org.jbookreader.book.bom.IContainerNode;
import org.jbookreader.book.bom.ISectioningNode;
import org.jbookreader.book.bom.SectioningNode;
import org.jbookreader.book.bom._TextNode;
import org.jbookreader.book.bom.internal.ContainerNode;
import org.jbookreader.book.stylesheet.EDisplayType;

/**
 * A very simple demonstration for the book model.
 * {@link org.jbookreader.book.parser.FB2Parser} was written using this demo.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 * @see org.jbookreader.book.Book 
 * @see org.jbookreader.book.bom 
 */
public class BookModelDemo {

	/**
	 * The book.
	 */
	private Book myBook;
	/**
	 * Current section.
	 */
	private ISectioningNode mySection;
	/**
	 * Current container.
	 */
	private IContainerNode myContainer;

	/**
	 * Main function. Creates simple book by running <code>run()</code>.
	 * @param args unused
	 */
	public static void main(String[] args) {
		new BookModelDemo().run();
	}

	/**
	 * Create a very simple book tree at <code>this.myBook</code>
	 *
	 */
	public void run() {
		// FixtionBook
		{
			this.myBook = new Book();
		}
		
		// body
		{
			BodyNode node  = new BodyNode();

			node.setTagName("body");

			if (this.myContainer != null) {
				this.myContainer.addChildNode(node);
			}
			
			this.myBook.addBody(node);
			
			this.mySection = node;

			this.myContainer = node;
		}
		
		// title
		{
			ContainerNode node = new ContainerNode();
			node.setDisplayType(EDisplayType.BLOCK);
			
			node.setTagName("title");
			
			if (this.myContainer != null) {
				this.myContainer.addChildNode(node);
			}

			this.mySection.setTitle(node);
			
			this.myContainer = node;
		}
		
		// p
		{
			ContainerNode node = new ContainerNode();
			node.setDisplayType(EDisplayType.BLOCK);

			node.setTagName("p");

			if (this.myContainer != null) {
				this.myContainer.addChildNode(node);
			}
			
			this.myContainer = node;
		}
		
		// #text
		{
			_TextNode node = new _TextNode();
			node.setText("Title");
			
			if (this.myContainer != null) {
				this.myContainer.addChildNode(node);
			}
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
			SectioningNode node = new SectioningNode();
			
			node.setTagName("section");
			
			if (this.myContainer != null) {
				this.myContainer.addChildNode(node);
			}

			this.mySection = node;

			this.myContainer = node;
		}
		// title
		{
			ContainerNode node = new ContainerNode();
			node.setDisplayType(EDisplayType.BLOCK);
			
			node.setTagName("title");
			
			if (this.myContainer != null) {
				this.myContainer.addChildNode(node);
			}

			this.mySection.setTitle(node);
			
			this.myContainer = node;
		}
		
		// p
		{
			ContainerNode node = new ContainerNode();
			node.setDisplayType(EDisplayType.BLOCK);
			
			node.setTagName("p");
			
			if (this.myContainer != null) {
				this.myContainer.addChildNode(node);
			}

			this.myContainer = node;
		}
		
		// #text
		{
			_TextNode node = new _TextNode();
			node.setText("Section Title");
			
			if (this.myContainer != null) {
				this.myContainer.addChildNode(node);
			}
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
			ContainerNode node = new ContainerNode();
			node.setDisplayType(EDisplayType.BLOCK);
			
			node.setTagName("p");
			
			if (this.myContainer != null) {
				this.myContainer.addChildNode(node);
			}

			this.myContainer = node;
		}
		
		// #text
		{
			_TextNode node = new _TextNode();
			node.setText("Section Text");
			
			if (this.myContainer != null) {
				this.myContainer.addChildNode(node);
			}
		}
		
		// em
		{
			ContainerNode node = new ContainerNode();
			node.setDisplayType(EDisplayType.INLINE);
			
			node.setTagName("em");
			
			this.myContainer.addChildNode(node);

			this.myContainer = node;
		}
		
		// #text
		{
			_TextNode node = new _TextNode();
			node.setText("in italic");
			
			if (this.myContainer != null) {
				this.myContainer.addChildNode(node);
			}
		}
		
		// /em
		{
			this.myContainer = this.myContainer.getParentNode();
		}
		
		// /p
		{
			this.myContainer = this.myContainer.getParentNode();
		}
		
		// /section
		{
			this.myContainer = this.myContainer.getParentNode();
			
			// FIXME: provide a way to do the same w/o typecast
			this.mySection = (ISectioningNode)(this.myContainer);
		}
		
		// /body
		{
			this.myContainer = this.myContainer.getParentNode();
			
			// FIXME: provide a way to do the same w/o typecast
			this.mySection = (ISectioningNode)(this.myContainer);
		}
		
		// /FictionBook
		{
			// empty
		}
	}

}
