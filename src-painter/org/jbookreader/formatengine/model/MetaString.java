package org.jbookreader.formatengine.model;

import org.jbookreader.formatengine.IRenderingObject;
import org.jbookreader.formatengine.IFont;
import org.jbookreader.formatengine.RenderingDimensions;

/**
 * This class represents a part of the string as a rendering object
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class MetaString implements IRenderingObject {
	/**
	 * Corresponding string.
	 */
	private final String myText;
	/**
	 * The index of starting symbol.
	 */
	private final int myStart;
	/**
	 * The index of the next symbol after the string.
	 */
	private final int myEnd;


	/**
	 * The font that should be used for the string.
	 */
	private final IFont myFont;

	/**
	 * Calculated string dimensions.
	 */
	private final RenderingDimensions myDimensions;

	/**
	 * This constructs new metastring.
	 * @param text the string, containing text of the object
	 * @param start the index of starting symbol
	 * @param end the index of the symbol next to the string
	 * @param font the font to render the string
	 */
	public MetaString(String text, int start, int end, IFont font) {
		this.myText = text;
		this.myStart = start;
		this.myEnd = end;
		this.myFont = font;
		
		this.myDimensions = font.calculateStringDimensions(this.myText, start, end);
	}
	
	public RenderingDimensions getDimensions() {
		return this.myDimensions;
	}

	public void render() {
		this.myFont.renderString(this.myText, this.myStart, this.myEnd, this.myDimensions);
	}

	public boolean isGlue() {
		return false;
	}
}
