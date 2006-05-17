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

/**
 * This class represents horizontal glue rendering object (whitespace).
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 * 
 */
public class HorizontalGlue extends AbstractInlineRenderingObject {

	/**
	 * This constructs new horizontal glue with specified parameters.
	 * 
	 * @param strut the size of glue object
	 * @param painter the painter for this object
	 * @param node the node corresponding to this glue object
	 */
	public HorizontalGlue(IBookPainter painter, INode node, double strut) {
		this(painter, node, strut, 0);
	}

	/**
	 * This constructs new horizontal glue with specified parameters.
	 * 
	 * @param strut the size of glue object
	 * @param painter the painter for this object
	 * @param node the node corresponding to this glue object
	 * @param height the height of the glue object
	 */
	public HorizontalGlue(IBookPainter painter, INode node, double strut, double height) {
		super(painter, node);
		setWidth(strut);
		setHeight(height);
	}

	@Override
	public void renderInline() {
		this.getPainter().addHorizontalStrut(this.getWidth());
	}

	@Override
	public void adjustWidth(double width) throws UnsupportedOperationException {
		setWidth(getWidth() + width);
	}

	@Override
	public int getAdjustability() {
		return 1;
	}

}
