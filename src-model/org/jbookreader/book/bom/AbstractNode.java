package org.jbookreader.book.bom;


/**
 * This class represents an abstract book node.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public abstract class AbstractNode implements INode {
	
	/**
	 * The tag-name of the node.
	 */
	private String myTagName;

	/**
	 * The ID of the node
	 */
	private String myID;

	/**
	 * The parent of this node.
	 */
	private IContainerNode myParentNode;

	public String getTagName() {
		return this.myTagName;
	}

	/**
	 * This sets the tag-name
	 * @param tagName the new tag-name
	 */
	protected void setTagName(String tagName) {
		this.myTagName = tagName;
	}

	public String getText() {
		return "";
	}

	public IContainerNode getParentNode() {
		return this.myParentNode;
	}

	@Deprecated
	public void setParentNode(IContainerNode parentNode) {
		if (this.myParentNode != null) {
			this.myParentNode.removeChildNode(this);
		}
		this.myParentNode = parentNode;
	}

	public boolean isContainer() {
		return false;
	}

	public String getID() {
		return this.myID;
	}

	@Deprecated
	public void setID(String id) {
		this.myID = id;
	}

}
