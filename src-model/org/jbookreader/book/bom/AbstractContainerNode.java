package org.jbookreader.book.bom;

import java.util.LinkedList;
import java.util.List;


/**
 * This class represents an abstract container node.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public abstract class AbstractContainerNode extends AbstractNode implements IContainerNode {
	
	/**
	 * The list of child nodes.
	 */
	private List<INode> myChildNodes = new LinkedList<INode>();

	@Override
	public boolean isContainer() {
		return true;
	}

	public List<INode> getChildNodes() throws UnsupportedOperationException {
		return this.myChildNodes;
	}
	
	@SuppressWarnings("deprecation")
	public void addChildNode(INode node) {
		this.myChildNodes.add(node);
		node.setParentNode(this);
	}

	public void removeChildNode(INode node) {
		this.myChildNodes.remove(node);
	}

	@Override
	public void setTagName(String tagName) {
		super.setTagName(tagName);
	}

	public boolean isSectioningNode() {
		return false;
	}

}
