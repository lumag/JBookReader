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
	 * Returns a collection of child (contained in this one) nodes.
	 * @return a collection of child nodes.
	 */
	List<INode> getChildNodes();

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
	 * Returns the title of the section.
	 * @return the title of the section.
	 */
	IContainerNode getTitle();
	/**
	 * Allocates the title of the section.
	 * @param tagName the tag name of the title node
	 * @return newly created container node.
	 */
	IContainerNode newTitle(String tagName);
	
	/**
	 * Allocates new Image node.
	 * @param tagName the tag name of the image node
	 * @param href the url of the image 
	 * @return newly created image node.
	 */
	IImageNode newImage(String tagName, String href);

}
