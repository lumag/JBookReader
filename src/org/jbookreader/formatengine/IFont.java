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
 * This interface represents a font used for outputting on the {@link org.jbookreader.formatengine.IBookPainter}
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public interface IFont {
	/**
	 * Returns the family name of the font.
	 * @return the family name of the font.
	 */
	String getFontFamily();

	/**
	 * Returns the size of the font.
	 * @return the size of the font.
	 */

	double getFontSize();
	/**
	 * Returns the width of space in this font.
	 * @return the width of space in this font.
	 */
	double getSpaceWidth();
	/**
	 * Calculates dimensions to be used for rendering the specified string part
	 * with given font.
	 * @param s the string to calculate dimensions for 
	 * @param start The index of the first character in the string
	 * @param end The index of the character following the last character in the subsequence
	 * @return the dimensions of the string when rendered.
	 */
	RenderingDimensions calculateStringDimensions(String s, int start, int end);
	/**
	 * Renders the string with given font
	 * @param s the string to render
	 * @param start The index of the first character in the string
	 * @param end The index of the character following the last character in the subsequence
	 */
	void renderString(String s, int start, int end);
	
}
