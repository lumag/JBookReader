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
package org.jbookreader.book.stylesheet;


/**
 * This interface represents a style stack &mdash; a model
 * of cascading styling information.
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public interface IStyleStack {
	/**
	 * Pushes new tag into the style stack.
	 * 
	 * @param name the name of the tag
	 * @param klass the class of the tag
	 * @param id the id of the tag
	 */
	void pushTag(String name, String klass, String id);

	/**
	 * Pops last tag from the style stack.
	 *
	 */
	void popTag();


	/**
	 * Returns the 'display' property of the node
	 * @return display type of the node.
	 */
	EDisplayType getDisplay();
	
	/**
	 * Returns the left margin for the node.
	 * @return the left margin for the node.
	 */
	double getMarginLeft();
	/**
	 * Returns the right margin for the node.
	 * @return the right margin for the node.
	 */
	double getMarginRight();
	/**
	 * Returns the top margin for the node.
	 * @return the top margin for the node.
	 */
	double getMarginTop();
	/**
	 * Returns the bottom margin for the node.
	 * @return the bottom margin for the node.
	 */
	double getMarginBottom();
	
	/**
	 * Returns the indentation for the first line of the node.
	 * @return the indentation for the first line of the node.
	 */
	double getTextIndent();
	/**
	 * Returns the relative line height.
	 * @return the relative line height.
	 */
	double getLineHeight();
	/**
	 * Returns font family.
	 * @return font family.
	 */
	String getFontFamily();
	/**
	 * Returns font size.
	 * @return font size.
	 */
	int getFontSize();

	/**
	 * Sets default font family.
	 * 
	 * @param fontFamily default font family
	 */
	void setDefaultFontFamily(String fontFamily);
	
	/**
	 * Sets default font size.
	 * 
	 * @param size default font size
	 */
	void setDefaultFontSize(int size);

	ETextAlign getTextAlign();
}
