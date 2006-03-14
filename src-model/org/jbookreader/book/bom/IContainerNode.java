package org.jbookreader.book.bom;

import java.util.Collection;

import org.jbookreader.book.stylesheet.EDisplayType;

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
	 * FIXME: remove second parameter!!!!!!!!
	 * @param tagName the tag name of new node
	 * @param type the display type of the node
	 * @return new container node.
	 */
	IContainerNode newContainerNode(String tagName, EDisplayType type);

	/**
	 * Returns true if this node also implements {@link ISectioningNode}
	 * @return true if this node is a sectioning node.
	 */
	boolean isSectioningNode();

}
