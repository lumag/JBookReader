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
package org.jbookreader.book.bom.impl;

import org.jbookreader.book.bom.IImageNode;


/**
 * This class represents an inline image.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class ImageNode extends AbstractNode implements IImageNode {
	/**
	 * Hyperlink reference to the image.
	 */
	private String myHyperRef;
	/**
	 * Alternative text
	 */
	private String myText;
	
	/**
	 * The title of the image.
	 */
	private String myTitle;
	
	@Override
	public String getText() {
		return this.myText;
	}

	public void setText(String text) {
		this.myText = text;
	}

	public String getHyperRef() {
		return this.myHyperRef;
	}

	/**
	 * Sets the location of the image.
	 * @param hyperRef the new location of the image.
	 * 
	 * @see ImageNode#getHyperRef()
	 */
	public void setHyperRef(String hyperRef) {
		this.myHyperRef = hyperRef;
	}

	public String getTitle() {
		return this.myTitle;
	}

	public void setTitle(String title) {
		this.myTitle = title;
	}

}
