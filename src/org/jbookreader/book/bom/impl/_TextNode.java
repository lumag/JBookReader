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


/**
 * This class represens a simple text string w/o formatting or anything else.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
class _TextNode extends AbstractNode {
	/**
	 * The text, contained in the node.
	 */
	private String myString = "";
	
	/**
	 * This constructs a new string node.
	 */
	_TextNode() {
		this.setTagName("#text");
	}

	@Override
	public String getText() {
		return this.myString;
	}

	/**
	 * Sets the node text.
	 * @param str the new text of the node
	 */
	public void setText(String str) {
		this.myString = str;
	}

}
