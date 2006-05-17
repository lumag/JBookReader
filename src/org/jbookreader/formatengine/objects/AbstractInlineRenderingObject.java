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
import org.jbookreader.formatengine.IInlineRenderingObject;

public abstract class AbstractInlineRenderingObject extends
		AbstractRenderingObject implements IInlineRenderingObject {
	
	private double myDepth;

	public AbstractInlineRenderingObject(IBookPainter painter, INode node) {
		super(painter, node);
	}

	public double getDepth() {
		return this.myDepth;
	}

	protected void setDepth(double depth) {
		this.myDepth = depth;
	}
	
	@Override
	public final void renderContents() {
		IBookPainter painter = getPainter();
		painter.addVerticalStrut(getHeight() - getDepth());
		renderInline();
		painter.addHorizontalStrut(-getWidth());
		painter.addVerticalStrut(getDepth());
	}

	public abstract void renderInline();

	public int getAdjustability() {
		return 0;
	}

	public void adjustWidth(double width) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("adjusting of this objects isn't supported");
	}
}
