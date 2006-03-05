package org.jbookreader.book.bom;

import org.jbookreader.book.stylesheet.EDisplayType;

/**
 * This class represens a simple text string w/o formatting or anything else.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class _TextNode extends AbstractNode {
	/**
	 * The text, contained in the node.
	 */
	private String myString = "";
	
	/**
	 * This constructs a new string node.
	 */
	public _TextNode() {
		this.setTagName("#text");
	}

	@Override
	public String getText() {
		return this.myString;
	}

	/**
	 * Sets the node text.
	 * @param str the new text of the node
	 */
	public void setText(String str) {
		this.myString = str;
	}

	public EDisplayType getDisplayType() {
		return EDisplayType.INLINE;
	}

}
