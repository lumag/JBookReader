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
package org.jbookreader.book.bom;

/**
 * This interface represents image node.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public interface IImageNode extends INode {

	/**
	 * Returns the location of the image. Probably you should only use internal locations ('#id') for now.
	 * @return the location of the image.
	 */
	String getHyperRef();

	/**
	 * Sets the alternative text for the image.
	 * @param text new alternative text
	 */
	void setText(String text);

	/**
	 * Returns the title of the image or null if there is no title.
	 * @return the title of the image
	 */
	String getTitle();

	/**
	 * Sets the title of the image.
	 * @param title new title
	 */
	void setTitle(String title);

}