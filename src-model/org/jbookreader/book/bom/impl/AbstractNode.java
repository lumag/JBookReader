package org.jbookreader.book.bom.impl;

import org.jbookreader.book.bom.IContainerNode;
import org.jbookreader.book.bom.INode;



/**
 * This class represents an abstract book node.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
abstract class AbstractNode implements INode {
	
	/**
	 * The tag-name of the node.
	 */
	private String myTagName;

	/**
	 * The ID of the node
	 */
	private String myID;
	
	/**
	 * The class of the node
	 */
	private String myNodeClass;

	/**
	 * The parent of this node.
	 */
	private IContainerNode myParentNode;
	
	/**
	 * The book to which this node belongs.
	 */
	private Book myBook;
	
	/**
	 * Returns the book, corresponding to this node.
	 * @return the book, corresponding to this node.
	 */
	public Book getBook() {
		return this.myBook;
	}
	
	/**
	 * Sets the book, corresponding to this node.
	 * @param book the book for this node.
	 */
	void setBook(Book book) {
		this.myBook = book;
	}

	public String getTagName() {
		return this.myTagName;
	}

	/**
	 * This sets the tag-name
	 * @param tagName the new tag-name
	 */
	protected void setTagName(String tagName) {
		this.myTagName = tagName;
	}

	public String getText() {
		return "";
	}

	public IContainerNode getParentNode() {
		return this.myParentNode;
	}

	/**
	 * Sets the parent node for this one.
	 * 
	 * @param parentNode the node, containing this one
	 */
	protected void setParentNode(IContainerNode parentNode) {
		this.myParentNode = parentNode;
	}

	public String getID() {
		return this.myID;
	}

	public void setID(String id) {
		this.myID = id;
		this.myBook.mapIdNode(this);
	}

	public String getNodeClass() {
		return this.myNodeClass;
	}

	public void setNodeClass(String nodeClass) {
		this.myNodeClass = nodeClass;
	}

}
