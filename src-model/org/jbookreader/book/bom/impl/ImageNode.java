package org.jbookreader.book.bom.impl;

import org.jbookreader.book.bom.IImageNode;


/**
 * This class represents an inline image.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class ImageNode extends AbstractNode implements IImageNode {
	/**
	 * Hyperlink reference to the image.
	 */
	private String myHyperRef;
	/**
	 * Alternative text
	 */
	private String myText;
	
	/**
	 * The title of the image.
	 */
	private String myTitle;
	
	@Override
	public String getText() {
		return this.myText;
	}

	public void setText(String text) {
		this.myText = text;
	}

	public String getHyperRef() {
		return this.myHyperRef;
	}

	/**
	 * Sets the location of the image.
	 * @param hyperRef the new location of the image.
	 * 
	 * @see ImageNode#getHyperRef()
	 */
	public void setHyperRef(String hyperRef) {
		this.myHyperRef = hyperRef;
	}

	public String getTitle() {
		return this.myTitle;
	}

	public void setTitle(String title) {
		this.myTitle = title;
	}

}
