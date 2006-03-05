package org.jbookreader.book.bom;

import org.jbookreader.book.stylesheet.EDisplayType;

/**
 * This class represents an inline image.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class ImageNode extends AbstractNode {
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
	
	private EDisplayType myDisplayType;

	/**
	 * This constructs new inline image node.
	 */
	public ImageNode() {
		this.setTagName("image");
		this.setDisplayType(EDisplayType.INLINE);
	}

	@Override
	public String getText() {
		return this.myText;
	}

	/**
	 * Sets the	 alternative text for the image.
	 * @param text new alternative text
	 */
	public void setText(String text) {
		this.myText = text;
	}

	public EDisplayType getDisplayType() {
		return this.myDisplayType;
	}

	public void setDisplayType(EDisplayType displayType) {
		this.myDisplayType = displayType;
	}

	/**
	 * Returns the location of the image. Probably you should only use internal locations ('#id') for now.
	 * @return the location of the image.
	 */
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

	/**
	 * Returns the title of the image or null if there is no title.
	 * @return the title of the image
	 */
	public String getTitle() {
		return this.myTitle;
	}

	/**
	 * Sets the title of the image.
	 * @param title new title
	 */
	public void setTitle(String title) {
		this.myTitle = title;
	}

}
