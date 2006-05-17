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
package org.jbookreader.util;

import java.io.InputStream;
import java.io.PrintWriter;

import org.jbookreader.book.bom.INode;
import org.jbookreader.formatengine.IBookPainter;
import org.jbookreader.formatengine.IInlineRenderingObject;
import org.jbookreader.formatengine.IFont;
import org.jbookreader.formatengine.RenderingDimensions;

/**
 * This class represents a simple painter over the console with width 80.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class TextPainter implements IBookPainter {
	
	private static final double EMULATED_FONT_SIZE = 12.0;

	private static final double EMULATED_LINE_SKIP = 0.2 * EMULATED_FONT_SIZE;

	/**
	 * Output device.
	 */
	private final PrintWriter myOutput;
	
	/**
	 * Current X coordinate.
	 */
	private int myX;

	/**
	 * The width of formatted text.
	 */
	private double myWidth;

	private double myRealX;
	private double myIntY;
	private double myRealY;

	/**
	 * This constructs new <code>TextPainter</code> with specified
	 * <code>PrintWriter</code> as the output device.
	 * @param output output stream
	 * @param width the width of text stram
	 */
	public TextPainter(PrintWriter output, int width) {
		this.myOutput = output;
		this.myWidth = width;
	}

	public void clear() {
		this.myX = 0;
		this.myRealX = 0;
		this.myIntY = 0;
		this.myRealY = 0;
	}

	public double getWidth() {
		return this.myWidth;
	}

	public double getHeight() {
		return Double.POSITIVE_INFINITY;
	}
	
	public void addHorizontalStrut(double size) {
//		new Throwable(this.myRealX + ":" + this.myX + ":" + size).printStackTrace(this.myOutput);
		this.myRealX += size;
	}

	public void addVerticalStrut(double size) {
//		new Throwable(this.myRealY + ":" + size).printStackTrace(this.myOutput);
		this.myRealY += size;
		while (this.myRealY >= (0.5 * (EMULATED_LINE_SKIP + EMULATED_FONT_SIZE))) {
			this.myRealY -= EMULATED_FONT_SIZE + EMULATED_LINE_SKIP;
			this.myIntY ++;
			this.myOutput.println();
			this.myX = 0;
		}
	}

	/**
	 * This closes the writer.
	 */
	public void close() {
		this.myOutput.close();
	}

	/**
	 * The w/a for the all font problems.
	 */
	private IFont myFont; 
	public IFont getFont(String name, int size) {
		if (this.myFont == null) {
			this.myFont = new IFont() {

				public double getSpaceWidth() {
					return 1;
				}

				public RenderingDimensions calculateStringDimensions(String s, int start, int end) {
					return new RenderingDimensions(EMULATED_FONT_SIZE, 0,  end - start);
				}

				public void renderString(String s, int start, int end) {
					TextPainter.this.renderString(s, start, end);
				}

				public String getFontFamily() {
					return "default";
				}

				public double getFontSize() {
					return EMULATED_FONT_SIZE;
				}

			};
		}
		return this.myFont;
	}

	void renderString(String s, int start, int end) {
		while (this.myX < this.myRealX -0.5) {
			this.myOutput.append(' ');
			this.myX ++;
		}
		this.myOutput.append(s, start, end);
		this.myX += end - start;
		this.myRealX += end - start;
	}

	public double getXCoordinate() {
		return this.myX;
	}
	public double getYCoordinate() {
		return this.myRealY + this.myIntY * (EMULATED_FONT_SIZE + EMULATED_LINE_SKIP);
	}

	public IInlineRenderingObject getImage(INode node, String contentType, InputStream stream) {
		// allways null: we can't render images.
		return null;
	}

}
