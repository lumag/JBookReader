package org.jbookreader.book.bom;

import org.jbookreader.book.stylesheet.EDisplayType;

/**
 * This interface represents an element of the book.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 * @see org.jbookreader.book.bom.IContainerNode
 */
public interface INode {
	/**
	 * Returns true if the node contains other nodes. 
	 * @return true if the node contains other nodes.
	 */
	boolean isContainer();

	/**
	 * Returns the text contained in the node.
	 * @return the text contained in the node.
	 */
	String getText();

	/**
	 * Returns a displaying type of the node.
	 * @return a displaying type of the node.
	 */
	EDisplayType getDisplayType();
	
	/**
	 * Returns the tag-name of the node.
	 * @return the tag-name of the node.
	 */
	String getTagName();
	
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
	
}
