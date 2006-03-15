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
	
	private ISectioningNode myParentSection;

	public IContainerNode getTitle() {
		return this.myTitle;
	}
	
	public Collection<IContainerNode> getEpigraph() {
		return Collections.unmodifiableCollection(this.myEpigraph);
	}

	public ImageNode getImage() {
		return this.myImage;
	}

	@Override
	public boolean isSectioningNode() {
		return true;
	}

	public IContainerNode getAnnotation() {
		return this.myAnnotation;
	}

	public ISectioningNode newSectioningNode(String tagName) {
		SectioningNode node = new SectioningNode();
		node.setTagName(tagName);
		this.addChildNode(node);
		node.setParentSection(this);
		return node;
	}

	public ISectioningNode getParentSection() {
		return this.myParentSection;
	}

	void setParentSection(ISectioningNode parentSection) {
		this.myParentSection = parentSection;
	}

	public IContainerNode newTitle(String tagName) {
		ContainerNode node = new ContainerNode();
		node.setTagName(tagName);
		this.setupChildNode(node);
		this.myTitle = node;
		return node;
	}

	public IContainerNode newEpigraph(String tagName) {
		ContainerNode node = new ContainerNode();
		node.setTagName(tagName);
		this.setupChildNode(node);
		this.myEpigraph.add(node);
		return node;
	}

	public ImageNode newImage(String tagName) {
		ImageNode node = new ImageNode();
		node.setTagName(tagName);
		this.setupChildNode(node);
		this.myImage = node;
		return node;
	}

	public IContainerNode newAnnotation(String tagName) {
		ContainerNode node = new ContainerNode();
		node.setTagName(tagName);
		this.setupChildNode(node);
		this.myAnnotation = node;
		return node;
	}

}
