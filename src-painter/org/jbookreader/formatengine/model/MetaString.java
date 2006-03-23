package org.jbookreader.formatengine.model;

import org.jbookreader.formatengine.ITextFont;
import org.jbookreader.formatengine.IBookPainter;

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
	 * Corresponding painter.
	 */
	private final IBookPainter myPainter;

	/**
	 * The font that should be used for the string.
	 */
	private final ITextFont myFont;

	/**
	 * Calculated string dimensions.
	 */
	private final RenderingDimensions myDimensions;

	/**
	 * This constructs new metastring.
	 * @param text the string, containing text of the object
	 * @param start the index of starting symbol
	 * @param end the index of the symbol next to the string
	 * @param painter corresponding painter
	 * @param font the font to render the string
	 */
	public MetaString(String text, int start, int end, IBookPainter painter, ITextFont font) {
		this.myText = text;
		this.myStart = start;
		this.myEnd = end;
		this.myFont = font;
		
		this.myPainter = painter;
		
		this.myDimensions = painter.calculateStringDimensions(this.myText, start, end, font);
	}
	
	public RenderingDimensions getDimensions() {
		return this.myDimensions;
	}

	public void render() {
		this.myPainter.renderString(this.myText, this.myStart, this.myEnd, this.myFont, this.myDimensions);
	}

	public boolean isGlue() {
		return false;
	}
}
