package org.jbookreader.book.bom;


/**
 * This interface represents an element of the book.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 * @see org.jbookreader.book.bom.IContainerNode
 */
public interface INode {
	/**
	 * Returns the container part of this node or null if the node isn't a container.
	 * @return the container part of this node.
	 */
	IContainerNode getContainer();

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
}
