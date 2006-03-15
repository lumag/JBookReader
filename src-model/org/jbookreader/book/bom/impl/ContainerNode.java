package org.jbookreader.book.bom.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jbookreader.book.bom.IContainerNode;
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

	@Override
	public boolean isContainer() {
		return true;
	}

	public Collection<INode> getChildNodes() throws UnsupportedOperationException {
		return Collections.unmodifiableCollection(this.myChildNodes);
	}
	
	/**
	 * This registers new child node for this container.
	 * @param node the node to register.
	 */
	protected void addChildNode(AbstractNode node) {
		this.myChildNodes.add(node);
		this.setupChildNode(node);
	}
	
	protected void setupChildNode(AbstractNode node) {
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

	public boolean isSectioningNode() {
		return false;
	}

}
