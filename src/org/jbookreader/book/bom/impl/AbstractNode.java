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

	public String getNodeReference() {
		// TODO: reenable id handling
//		if (this.myID != null) {
//			return "#" + this.myID;
//		}
		if (this.myParentNode == null) {
			// FIXME! Body names
			return "0";
		}
		int index = this.myParentNode.getChildNodes().indexOf(this);
		return this.myParentNode.getNodeReference() + ";" + index;
	}

}
