package org.jbookreader.book.bom.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jbookreader.book.bom.IContainerNode;
import org.jbookreader.book.bom.ISectioningNode;

/**
 * This class represents an abstract section.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
class SectioningNode extends ContainerNode implements ISectioningNode {
	
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

	public IContainerNode getTitle() {
		return this.myTitle;
	}

	public void setTitle(IContainerNode title) {
		this.myTitle = title;
	}

	public Collection<IContainerNode> getEpigraph() {
		return Collections.unmodifiableCollection(this.myEpigraph);
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

	public ISectioningNode newSectioningNode(String tagName) {
		SectioningNode node = new SectioningNode();
		node.setTagName(tagName);
		this.addChildNode(node);
		return node;
	}
}
