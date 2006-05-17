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
package org.jbookreader.formatengine;

/**
 * This class represents dimensions of the rendering object for precise positioning.
 * As usual, these dimensions don't specify 'the real dimensions', i.e. there can be pixels
 * higher than <code>StringDimesions.height</code> over the baseline, but you should
 * use the specified numbers for string positioning.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class RenderingDimensions {
	/**
	 * The height of the string over the baseline.
	 */
	public final double ascent;
	/**
	 * The height of the string below the baseline.
	 */
	public final double depth;
	/**
	 * The width of the string.
	 */
	public final double width;

	/**
	 * Constructs new dimensions object from specified numbers.
	 * @param ascent the height of the string over the baseline.
	 * @param depth the height of the string below the baseline.
	 * @param width the width of the string.
	 */
	public RenderingDimensions(double ascent, double depth, double width) {
		this.ascent = ascent;
		this.depth = depth;
		this.width = width;
	}
	
}
