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
import org.jbookreader.formatengine.IRenderingObject;

/**
 * This class represents an abstract rendering object.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public abstract class AbstractRenderingObject implements IRenderingObject {
	interface BoxSides {
		int TOP = 0;
		int RIGHT = 1;
		int BOTTOM = 2;
		int LEFT = 3;
	}
	/**
	 * The height of the rendering object above the baseline.
	 */
	private double myHeight;

	/**
	 * The width of the rendering object
	 */
	private double myWidth;

	private double[] myMargins = new double[4];

	private INode myNode;

	private IBookPainter myPainter;

	protected AbstractRenderingObject(IBookPainter painter, INode node) {
		this.myPainter = painter;
		this.myNode = node;
	}

	public double getHeight() {
		return this.myHeight;
	}

	public double getWidth() {
		return this.myWidth;
	}

	protected void setHeight(double height) {
		this.myHeight = height;
	}

	protected void setWidth(double width) {
		this.myWidth = width;
	}

	public INode getNode() {
		return this.myNode;
	}

	public IBookPainter getPainter() {
		return this.myPainter;
	}
	
	public final void render() {
		this.getPainter().addHorizontalStrut(this.myMargins[BoxSides.LEFT]);
		renderContents();
		this.getPainter().addHorizontalStrut( - this.myMargins[BoxSides.LEFT]);
	}

	public abstract void renderContents();

	public void setMarginBottom(double margin) {
		this.myMargins[BoxSides.BOTTOM] = margin;
	}

	public void setMarginLeft(double margin) {
		this.myMargins[BoxSides.LEFT] = margin;
	}

	public void setMarginRight(double margin) {
		this.myMargins[BoxSides.RIGHT] = margin;
	}

	public void setMarginTop(double margin) {
		this.myMargins[BoxSides.TOP] = margin;
	}

	public boolean isSplittable() {
		return false;
	}

}
