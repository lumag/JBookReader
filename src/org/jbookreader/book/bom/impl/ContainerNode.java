/*
 * JBookReader - Java FictionBook Reader
 * Copyright (C) 2006 Dmitry Baryshkov
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *   
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *   
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the Free Software
 *   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.jbookreader.book.bom.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jbookreader.book.bom.IContainerNode;
import org.jbookreader.book.bom.IImageNode;
import org.jbookreader.book.bom.INode;


/**
 * This class represents an abstract container node.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
class ContainerNode extends AbstractNode implements IContainerNode {
	
	/**
	 * The list of child nodes.
	 */
	private List<INode> myChildNodes = new LinkedList<INode>();
	/**
	 * The title of the section.
	 */
	private IContainerNode myTitle;

	public List<INode> getChildNodes() {
		return Collections.unmodifiableList(this.myChildNodes);
	}
	
	/**
	 * This registers new child node for this container.
	 * @param node the node to register.
	 */
	protected void addChildNode(AbstractNode node) {
		this.myChildNodes.add(node);
		node.setBook(this.getBook());
		node.setParentNode(this);
	}
	
	public INode newTextNode(String text) {
		_TextNode node = new _TextNode();
		node.setText(text);
		this.addChildNode(node);
		return node;
	}

	public IContainerNode newContainerNode(String tagName) {
		ContainerNode node = new ContainerNode();
		node.setTagName(tagName);
		this.addChildNode(node);
		return node;
	}

	@Override
	public void setTagName(String tagName) {
		super.setTagName(tagName);
	}

	public IContainerNode getTitle() {
		return this.myTitle;
	}

	public IContainerNode newTitle(String tagName) {
		ContainerNode node = new ContainerNode();
		node.setTagName(tagName);
		this.addChildNode(node);
		this.myTitle = node;
		return node;
	}

	public IImageNode newImage(String tagName, String href) {
		ImageNode node = new ImageNode();
		node.setTagName(tagName);
		node.setHyperRef(href);
		this.addChildNode(node);
		return node;
	}

}
