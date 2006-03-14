package org.jbookreader;

import org.jbookreader.book.bom.IBook;
import org.jbookreader.book.bom.IContainerNode;
import org.jbookreader.book.bom.ISectioningNode;
import org.jbookreader.book.bom.impl.Book;
import org.jbookreader.book.stylesheet.EDisplayType;

/**
 * A very simple demonstration for the book model.
 * {@link org.jbookreader.book.parser.FB2Parser} was written using this demo.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 * @see org.jbookreader.book.bom.impl.Book 
 * @see org.jbookreader.book.bom 
 */
public class BookModelDemo {

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
			ISectioningNode node  = this.myBook.newBody("body", null);

			this.mySection = node;

			this.myContainer = node;
		}
		
		// title
		{
			IContainerNode node = this.myContainer.newContainerNode("title", EDisplayType.BLOCK);

			this.mySection.setTitle(node);
			
			this.myContainer = node;
		}
		
		// p
		{
			IContainerNode node = this.myContainer.newContainerNode("p", EDisplayType.BLOCK);
			
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
			IContainerNode node = this.myContainer.newContainerNode("title", EDisplayType.BLOCK);

			this.mySection.setTitle(node);
			
			this.myContainer = node;
		}
		
		// p
		{
			IContainerNode node = this.myContainer.newContainerNode("p", EDisplayType.BLOCK);

			this.myContainer = node;
		}
		
		// #text
		{
			this.myContainer.newTextNode("Section Title");
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
			IContainerNode node = this.myContainer.newContainerNode("p", EDisplayType.BLOCK);

			this.myContainer = node;
		}
		
		// #text
		{
			this.myContainer.newTextNode("Section Text");
		}
		
		// em
		{
			IContainerNode node = this.myContainer.newContainerNode("em", EDisplayType.INLINE);

			this.myContainer = node;
		}
		
		// #text
		{
			this.myContainer.newTextNode("in italic");
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
