package org.jbookreader.book.bom;

import java.util.Collection;


/**
 * This interface represents a container node.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public interface IContainerNode extends INode {

	/**
	 * Returns a collection of child (contained in this one) nodes.
	 * @return a collection of child nodes.
	 */
	Collection<INode> getChildNodes();

	/**
	 * This creates new child text node with specified text.
	 * @param text the text of created node
	 * @return new text node.
	 */
	INode newTextNode(String text);
	/**
	 * Creates new child container node.
	 * @param tagName the tag name of new node
	 * @return new container node.
	 */
	IContainerNode newContainerNode(String tagName);

	/**
	 * Returns true if this node also implements {@link ISectioningNode}
	 * @return true if this node is a sectioning node.
	 */
	boolean isSectioningNode();

}
