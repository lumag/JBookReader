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
package org.jbookreader.formatengine.objects;

import org.jbookreader.book.bom.INode;
import org.jbookreader.formatengine.IBookPainter;
import org.jbookreader.formatengine.IFont;
import org.jbookreader.formatengine.RenderingDimensions;

/**
 * This class represents a part of the string as a rendering object
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 * 
 */
public class MetaString extends AbstractInlineRenderingObject {
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
	 * @param painter the painter for this object
	 * @param node the node corresponding to this string
	 * @param lineHeight the relative line height
	 * @param text the string, containing text of the object
	 * @param start the index of starting symbol
	 * @param end the index of the symbol next to the string
	 * @param font the font to render the string
	 */
	public MetaString(IBookPainter painter, INode node, double lineHeight, String text, int start, int end, IFont font) {
		super(painter, node);

		this.myText = text;
		this.myStart = start;
		this.myEnd = end;
		this.myFont = font;

		RenderingDimensions dim = font.calculateStringDimensions(this.myText,
				start, end);
		
		double height = lineHeight * this.myFont.getFontSize();
		
		this.myHalfLeading = (height - dim.ascent)/2;
		setHeight(height);
		setDepth(dim.depth + this.myHalfLeading);
		setWidth(dim.width);
	}

	@Override
	public void renderInline() {
		this.myFont.renderString(this.myText, this.myStart, this.myEnd);
	}

}
