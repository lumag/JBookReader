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

import java.util.LinkedList;
import java.util.List;

import org.jbookreader.book.bom.INode;
import org.jbookreader.formatengine.IBookPainter;
import org.jbookreader.formatengine.IInlineRenderingObject;

/**
 * This class represents the main object of rendering engine &mdash; a single line.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 * 
 */
public class Line extends AbstractInlineRenderingObject {
	/**
	 * The list of contained rendered objects.
	 */
	private final List<IInlineRenderingObject> myRObjects = new LinkedList<IInlineRenderingObject>();
	
	private int myAdjustability;
	
	/**
	 * This constructs new line for specified painter.
	 * @param painter the painter on which this line will be rendered
	 * @param node the paragraph node to which the line corresponds
	 */
	public Line(IBookPainter painter, INode node) {
		super(painter, node);
	}

	/**
	 * Appends a rendering object to the line.
	 * TODO: Vertical align support 
	 * 
	 * @param object the object to append
	 */
	public void appendObject(IInlineRenderingObject object) {
		this.myRObjects.add(object);
		adjustProperties(object);
	}
		
	/**
	 * Prepends a rendering object to the line.
	 * TODO: Vertical align support 
	 * 
	 * @param object the object to prepend
	 */
	public void prependObject(IInlineRenderingObject object) {
		this.myRObjects.add(0, object);
		adjustProperties(object);
	}
		
	private void adjustProperties(IInlineRenderingObject object) {
		double oldOver = this.getHeight() - this.getDepth();
		double newOver = object.getHeight() - object.getDepth();
		
		double oDepth = object.getDepth();
		
		if (oDepth > this.getDepth()) {
			this.setDepth(oDepth);
		}
		
		if (newOver > oldOver) {
			setHeight(newOver + this.getDepth());
		}

		setWidth(getWidth() + object.getWidth());
		
		this.myAdjustability += object.getAdjustability();
	}

	@Override
	public void renderInline() {
//		IBookPainter painter = this.getPainter();
//		double y = painter.getYCoordinate() + getDepth();
//		if (y > painter.getHeight()
//			|| y < getHeight()) {
//			painter.addHorizontalStrut(getWidth());
//			return;
//		}
//
		for (IInlineRenderingObject robject : this.myRObjects) {
			robject.renderInline();
		}
	}

//	public List<IInlineRenderingObject> getObjects() {
//		return Collections.unmodifiableList(this.myRObjects);
//	}

	@Override
	public int getAdjustability() {
		return this.myAdjustability;
	}

	@Override
	public void adjustWidth(double width) throws UnsupportedOperationException {
		int adjustability = this.myAdjustability;
		if (adjustability == 0) {
			return;
		}
		setWidth(getWidth() + width);
		for (IInlineRenderingObject robject: this.myRObjects) {
			if (adjustability == 0) {
				break;
			}
			int currentAdjustability = robject.getAdjustability();
			if (currentAdjustability != 0) {
				double adjWidth = width * currentAdjustability / adjustability;
				adjustability -= currentAdjustability;
				robject.adjustWidth(adjWidth);
				width -= adjWidth;
			}
		}
	}
}
