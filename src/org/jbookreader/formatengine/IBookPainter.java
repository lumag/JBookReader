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

import java.io.InputStream;

import org.jbookreader.book.bom.INode;

/**
 * This interface represents a not-so-simple displaying device.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 * 
 */
public interface IBookPainter {
	/**
	 * Clears the output device, if it makes sense.
	 * 
	 */
	void clear();

	/**
	 * Returns the width of the device.
	 * 
	 * @return the width of the device.
	 */
	double getWidth();

	/**
	 * Returns the height of the device.
	 * 
	 * @return the height of the device.
	 */
	double getHeight();

	/**
	 * This adds the horizontal space of given size.
	 * 
	 * @param size the size of necessary horizontal space.
	 */
	void addHorizontalStrut(double size);

	/**
	 * This adds the vertical space of given size.
	 * 
	 * @param size the size of necessary vertical space.
	 */
	void addVerticalStrut(double size);

	/**
	 * Allocates and returns specified font.
	 * 
	 * @param name the name of the font
	 * @param size font size
	 * @return the allocated font object.
	 */
	IFont getFont(String name, int size);

	/**
	 * Returns current X coordinate.
	 * 
	 * @return current X coordinate.
	 */
	double getXCoordinate();

	/**
	 * Returns current Y coordinate.
	 * 
	 * @return current Y coordinate.
	 */
	double getYCoordinate();

	/**
	 * Returns a rendering object for the image or <code>null</code> if
	 * the image can't be rendered.
	 * @param node the node representing the image
	 * @param contentType the content type of the image
	 * @param stream the stream with the image
	 * 
	 * @return a rendering object for the image.
	 */
	IInlineRenderingObject getImage(INode node, String contentType, InputStream stream);
}
