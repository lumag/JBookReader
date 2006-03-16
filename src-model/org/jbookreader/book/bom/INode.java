package org.jbookreader.book.bom;


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
	
	/**
	 * Returns a book containing this node.
	 * @return a book containing this node.
	 */
	IBook getBook();
}
