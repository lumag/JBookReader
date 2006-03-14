package org.jbookreader.book.bom.impl;

import org.jbookreader.book.bom.IBook;
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
	 * The parent of this node.
	 */
	private IContainerNode myParentNode;
	
	/**
	 * The book to which this node belongs.
	 */
	private IBook myBook;
	
	/**
	 * Returns the book, corresponding to this node.
	 * @return the book, corresponding to this node.
	 */
	IBook getBook() {
		return this.myBook;
	}
	
	/**
	 * Sets the book, corresponding to this node.
	 * @param book the book for this node.
	 */
	void setBook(IBook book) {
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
	void setParentNode(IContainerNode parentNode) {
		this.myParentNode = parentNode;
	}

	public boolean isContainer() {
		return false;
	}

	public String getID() {
		return this.myID;
	}

	/**
	 * Sets the ID of the node.
	 * @param id new id of the node
	 */
	void setID(String id) {
		this.myID = id;
	}

}
