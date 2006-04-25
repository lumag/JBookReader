package org.jbookreader.formatengine.objects;

import org.jbookreader.book.bom.INode;
import org.jbookreader.book.stylesheet.IStyleStack;
import org.jbookreader.formatengine.AbstractRenderingObject;
import org.jbookreader.formatengine.IBookPainter;
import org.jbookreader.formatengine.IFont;
import org.jbookreader.formatengine.RenderingDimensions;

/**
 * This class represents a part of the string as a rendering object
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 * 
 */
public class MetaString extends AbstractRenderingObject {
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
	
	private final double myHalfLeading;

	/**
	 * This constructs new metastring.
	 * @param styleStack TODO
	 * @param text the string, containing text of the object
	 * @param start the index of starting symbol
	 * @param end the index of the symbol next to the string
	 * @param font the font to render the string
	 */
	public MetaString(IBookPainter painter, INode node, IStyleStack styleStack, String text, int start, int end, IFont font) {
		super(painter, node);

		this.myText = text;
		this.myStart = start;
		this.myEnd = end;
		this.myFont = font;

		RenderingDimensions dim = font.calculateStringDimensions(this.myText,
				start, end);
		
		double height = styleStack.getLineHeight() * this.myFont.getFontSize();
		
		this.myHalfLeading = (height - dim.ascent)/2;
		setHeight(height);
		setDepth(dim.depth + this.myHalfLeading);
		setWidth(dim.width);
	}

	public void render() {
		this.myFont.renderString(this.myText, this.myStart, this.myEnd);
	}

}
