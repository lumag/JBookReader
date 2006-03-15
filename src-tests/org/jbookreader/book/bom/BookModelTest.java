package org.jbookreader.book.bom;

import org.jbookreader.book.bom.impl.Book;

/**
 * A very simple demonstration for the book model.
 * {@link org.jbookreader.book.parser.FB2Parser} was written using this demo.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 * @see org.jbookreader.book.bom.impl.Book 
 * @see org.jbookreader.book.bom 
 */
public class BookModelTest {

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
		new BookModelTest().run();
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
			IContainerNode node = this.myContainer.newContainerNode("p");

			this.myContainer = node;
		}
		
		// #text
		{
			this.myContainer.newTextNode("Section Text");
		}
		
		// em
		{
			IContainerNode node = this.myContainer.newContainerNode("em");

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

}
