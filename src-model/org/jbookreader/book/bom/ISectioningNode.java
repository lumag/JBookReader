package org.jbookreader.book.bom;

import java.util.List;

/**
 * This interface represents a sectioning node.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public interface ISectioningNode {

	/**
	 * Returns the title of the section.
	 * @return the title of the section.
	 */
	IContainerNode getTitle();
	/**
	 * Sets the title of the section.
	 * @param title the new title node
	 */
	void setTitle(IContainerNode title);

	/**
	 * Returns the list of epigraphs for the section.
	 * @return the list of epigraph nodes.
	 */
	List<IContainerNode> getEpigraph();
	
	/**
	 * Adds new epigraph for this section
	 * @param epigraph new epigraph
	 */
	void addEpigraph(IContainerNode epigraph);

	/**
	 * Returns the image for this section.
	 * @return the image for this section.
	 */
	ImageNode getImage();
	
	/**
	 * Sets the image for this section.
	 * @param image new image of this section
	 */
	void setImage(ImageNode image);

	/**
	 * Returns the annotation for the section.
	 * @return the annotation for the section.
	 */
	IContainerNode getAnnotation();
	
	/**
	 * Sets the annotation for the section.
	 * @param annotation new annotation.
	 */
	void setAnnotation(IContainerNode annotation);

}