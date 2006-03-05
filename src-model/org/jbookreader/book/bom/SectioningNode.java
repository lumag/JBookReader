package org.jbookreader.book.bom;

import java.util.LinkedList;
import java.util.List;

import org.jbookreader.book.stylesheet.EDisplayType;

/**
 * This class represents an abstract section.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class SectioningNode extends AbstractContainerNode implements ISectioningNode {
	
	/**
	 * The title of the section.
	 */
	private IContainerNode myTitle;
	/**
	 * The list of epigraphs.
	 */
	private List<IContainerNode> myEpigraph = new LinkedList<IContainerNode>();
	/**
	 * Associated image.
	 */
	private ImageNode myImage;
	/**
	 * Annotation of the section.
	 */
	private IContainerNode myAnnotation;

	public EDisplayType getDisplayType() {
		return EDisplayType.BLOCK;
	}

	public IContainerNode getTitle() {
		return this.myTitle;
	}

	public void setTitle(IContainerNode title) {
		this.myTitle = title;
	}

	public List<IContainerNode> getEpigraph() {
		return this.myEpigraph;
	}

	public void addEpigraph(IContainerNode epigraph) {
		this.myEpigraph.add(epigraph);
	}

	public ImageNode getImage() {
		return this.myImage;
	}

	public void setImage(ImageNode image) {
		this.myImage = image;
	}

	@Override
	public boolean isSectioningNode() {
		return true;
	}

	public IContainerNode getAnnotation() {
		return this.myAnnotation;
	}

	public void setAnnotation(IContainerNode annotation) {
		this.myAnnotation = annotation;
	}
}
