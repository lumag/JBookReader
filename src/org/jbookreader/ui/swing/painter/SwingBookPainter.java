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

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.InputStream;

import org.jbookreader.book.bom.INode;
import org.jbookreader.formatengine.IBookPainter;
import org.jbookreader.formatengine.IFont;
import org.jbookreader.formatengine.IInlineRenderingObject;

/**
 * This is swing-based implementation of the {@link org.jbookreader.formatengine.IBookPainter}.
 * Currently it calls all {@link org.jbookreader.formatengine.impl.FormatEngine} from Swing tread.
 * @TODO move calling to other tread, make engine paint over buffering image, etc.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class SwingBookPainter implements IBookPainter {
	/**
	 * The width of the corresponding component.
	 */
	private double myWidth;
	/**
	 * The height of the corresponding component.
	 */
	private double myHeight;
	/**
	 * Current graphics object
	 */
	private Graphics2D myGraphics;
	
	/**
	 * Current X.
	 */
	private double myCurrentX;
	/**
	 * Current Y.
	 */
	private double myCurrentY;
	
	/**
	 * Sets the Graphics2D object for rendering
	 * @param g2d the G2D object
	 * @param width new width
	 * @param height new height
	 */
	public void setGraphics(Graphics2D g2d, double width, double height) {
		this.myGraphics = g2d;
		this.myWidth = width;
		this.myHeight = height;
		this.myCurrentX = this.myCurrentY = 0;
	}

	public void clear() {
		this.myGraphics.setColor(this.myGraphics.getBackground());
		this.myGraphics.fill(this.myGraphics.getClip());
		this.myGraphics.setColor(Color.BLACK);

		this.myCurrentX = this.myCurrentY = 0;
	}

	public double getHeight() {
		return this.myHeight;
	}

	public double getWidth() {
		return this.myWidth;
	}

	public void addHorizontalStrut(double size) {
		this.myCurrentX += size;
	}

	public void addVerticalStrut(double size) {
		this.myCurrentY += size;
	}

	public IFont getFont(String name, int size) {
		return new SwingFont(this, name, size);
	}

	public double getXCoordinate() {
		return this.myCurrentX;
	}

	public double getYCoordinate() {
		return this.myCurrentY;
	}

	public IInlineRenderingObject getImage(INode node, String contentType, InputStream stream) {
		try {
			return new ImageRenderingObject(this, node, stream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns the graphics rendering context.
	 * @return the graphics rendering context.
	 */
	Graphics2D getGraphics() {
		return this.myGraphics;
	}

}