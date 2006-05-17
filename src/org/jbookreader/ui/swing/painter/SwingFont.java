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
package org.jbookreader.ui.swing.painter;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;

import org.jbookreader.formatengine.IFont;
import org.jbookreader.formatengine.RenderingDimensions;

/**
 * This is internal class used for representing Swing fonts for the FE.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
class SwingFont implements IFont {
	/**
	 * 
	 */
	private final SwingBookPainter myPainter;
	/**
	 * Font object from AWT
	 */
	final Font myFont;
	/**
	 * The width of single space in this font.
	 */
	double mySpaceWidth = 0; 
	
	/**
	 * This constructs the class representing the font with given name and size
	 * @param name the name of the font
	 * @param size the size of the font
	 * @param painter the painter containing this font
	 */
	public SwingFont(SwingBookPainter painter, String name, int size) {
		this.myPainter = painter;
		this.myFont = new Font(name, Font.PLAIN, size);
	}

	public double getSpaceWidth() {
		if (this.mySpaceWidth == 0) {
			FontRenderContext frc = this.myPainter.getGraphics().getFontRenderContext();
			GlyphVector gv = this.myFont.createGlyphVector(frc, new char[]{' '});
			Rectangle2D r2d = gv.getLogicalBounds();
			this.mySpaceWidth = r2d.getMaxX() - r2d.getMinX();
		}
		
		return this.mySpaceWidth;
	}

	/**
	 * Returns the corresponding Swing Font object.
	 * @return the corresponding Swing Font object.
	 */
	public Font getFont() {
		return this.myFont;
	}

	public RenderingDimensions calculateStringDimensions(String s, int start, int end) {
		FontRenderContext frc = this.myPainter.getGraphics().getFontRenderContext();
		LineMetrics metrics = this.myFont.getLineMetrics(s, start, end, frc);
		Rectangle2D r2d = this.myFont.getStringBounds(s, start, end, frc);
		return new RenderingDimensions(metrics.getAscent(), metrics.getDescent(), r2d.getMaxX() - r2d.getMinX());
	}

	public void renderString(String s, int start, int end) {
		Graphics2D graphics = this.myPainter.getGraphics();
		if (!graphics.getFont().equals(this.myFont)) {
			graphics.setFont(this.myFont);
		}

		graphics.drawString(s.substring(start, end),
				(float)this.myPainter.getXCoordinate(),
				(float)this.myPainter.getYCoordinate());
		RenderingDimensions dim = calculateStringDimensions(s, start, end);
		this.myPainter.addHorizontalStrut(dim.width);
	}

	public String getFontFamily() {
		return this.myFont.getFamily();
	}

	public double getFontSize() {
		return this.myFont.getSize2D();
	}

}