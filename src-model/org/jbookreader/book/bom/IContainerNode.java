package org.jbookreader.book.bom;

import java.util.List;

/**
 * This interface represents a container node.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public interface IContainerNode extends INode {

	/**
	 * Returns a list of child (contained in this one) nodes.
	 * @return a list of child nodes.
	 */
	List<INode> getChildNodes();
	/**
	 * Adds new node to the list of child nodes.
	 * @param node new node to add
	 */
	public void addChildNode(INode node);

	/**
	 * Removes given child node from this list of children.
	 * @param node a node to remove.
	 */
	void removeChildNode(INode node);

	/**
	 * This sets the tag-name
	 * @param tagName the new tag-name
	 */
	void setTagName(String tagName);

	/**
	 * Returns true if this node also implements {@link ISectioningNode}
	 * @return true if this node is a sectioning node.
	 */
	boolean isSectioningNode();

}
