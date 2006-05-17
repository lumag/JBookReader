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


/**
 * This interface represents an element of the book.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 * @see org.jbookreader.book.bom.IContainerNode
 */
public interface INode {

	/**
	 * Returns the text contained in the node.
	 * @return the text contained in the node.
	 */
	String getText();

	/**
	 * Returns the tag-name of the node.
	 * @return the tag-name of the node.
	 */
	String getTagName();
	
	/**
	 * Returns the class of the node.
	 * @return the class of the node.
	 */
	String getNodeClass();
	
	/**
	 * Sets the class of the node.
	 * @param nodeClass the class of the node
	 */
	void setNodeClass(String nodeClass);
	
	/**
	 * Returns the parent node (the node, containing this one).
	 * @return the parent node.
	 */
	IContainerNode getParentNode();
	
	/**
	 * Returns the ID of this node or null if this node has no ID.
	 * @return  the ID of this node.
	 */
	String getID();
	
	/**
	 * Sets the ID of this node
	 * @param id new ID
	 */
	void setID(String id);
	
	/**
	 * Returns a book containing this node.
	 * @return a book containing this node.
	 */
	IBook getBook();
	
	String getNodeReference();
}
