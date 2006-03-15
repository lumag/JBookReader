package org.jbookreader.book.bom;

import java.util.Collection;

import org.jbookreader.book.bom.impl.ImageNode;

/**
 * This interface represents a sectioning node.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public interface ISectioningNode extends IContainerNode {

	/**
	 * Returns the title of the section.
	 * @return the title of the section.
	 */
	IContainerNode getTitle();
	/**
	 * Allocates the title of the section.
	 * @param tagName the tag name of the title node
	 */
	IContainerNode newTitle(String tagName);

	/**
	 * Returns the list of epigraphs for the section.
	 * @return the list of epigraph nodes.
	 */
	Collection<IContainerNode> getEpigraph();
	
	/**
	 * Adds new epigraph for this section
	 * @param tagName the tag name of the epigraph
	 */
	IContainerNode newEpigraph(String tagName);

	/**
	 * Returns the image for this section.
	 * @return the image for this section.
	 */
	ImageNode getImage();
	
	/**
	 * Allocates the image for this section.
	 * @param tagName the tag name
	 */
	ImageNode newImage(String tagName);

	/**
	 * Returns the annotation for the section.
	 * @return the annotation for the section.
	 */
	IContainerNode getAnnotation();
	
	/**
	 * Allocates the annotation for the section.
	 * @param tagName the tag name
	 */
	IContainerNode newAnnotation(String tagName);
	
	/**
	 * This creates new child sectioning node and returns it.
	 * @param tagName the tag name of created section
	 * @return new sectioning node.
	 */
	ISectioningNode newSectioningNode(String tagName);

	ISectioningNode getParentSection();
}