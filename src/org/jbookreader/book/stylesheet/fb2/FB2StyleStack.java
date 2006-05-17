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
package org.jbookreader.book.stylesheet.fb2;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.jbookreader.book.stylesheet.EDisplayType;
import org.jbookreader.book.stylesheet.ETextAlign;
import org.jbookreader.book.stylesheet.IStyleStack;

class FB2StyleStack implements IStyleStack {
	
	private String myFontFamily = "Serif";
	private int myFontSize = 12;
	
	private List<SimpleNode> myNodesList = new ArrayList<SimpleNode>();
	
	public void pushTag(String name, String klass, String id) {
		this.myNodesList.add(new SimpleNode(name, klass, id));
	}

	public void popTag() {
		this.myNodesList.remove(this.myNodesList.size()-1);
	}

	public EDisplayType getDisplay() {
		ListIterator<SimpleNode> iterator = this.myNodesList.listIterator(this.myNodesList.size());
		
		SimpleNode node = iterator.previous();

		// special handling for images
		if (node.name.equals("image")) {
			while (iterator.hasPrevious()) {
				SimpleNode cnode = iterator.previous();
				if (FB2StyleSheet.getDisplay(cnode.name) == EDisplayType.INLINE) {
					return EDisplayType.INLINE;
				}
				if (cnode.name.equals("p") ||
				    cnode.name.equals("v") ||
				    cnode.name.equals("subtitle") ||
				    cnode.name.equals("text-author") ||
				    cnode.name.equals("coverpage")) {
					return EDisplayType.INLINE;
				}
			}
			return EDisplayType.BLOCK;
		}

		EDisplayType result = FB2StyleSheet.getDisplay(node.name);
		if (result != null) {
			return result;
		}

		System.err.println("Got unknown tag: '"+ node.name + "'! Check your parser version, please");

		// sane default
		return EDisplayType.BLOCK;
	}

	public double getMarginLeft() {
		SimpleNode node = this.myNodesList.get(this.myNodesList.size()-1);
		if (node.name.equals("p")) {
			return 5;
		}
		return 0;
	}

	public double getMarginRight() {
		SimpleNode node = this.myNodesList.get(this.myNodesList.size()-1);
		if (node.name.equals("p")) {
			return 5;
		}
		return 0;
	}

	public double getMarginBottom() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getMarginTop() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getTextIndent() {
		SimpleNode node = this.myNodesList.get(this.myNodesList.size()-1);
		if (node.name.equals("p")) {
			return 5;
		}
		return 0;
	}

	public double getLineHeight() {
		return 1.2;
	}
	
	public String getFontFamily() {
		return this.myFontFamily;
	}
	
	public int getFontSize() {
		return this.myFontSize;
	}

	public void setDefaultFontFamily(String fontFamily) {
		this.myFontFamily = fontFamily;
	}

	public void setDefaultFontSize(int size) {
		this.myFontSize = size;
	}

	public ETextAlign getTextAlign() {
		// XXX: hack!
		SimpleNode parentNode = this.myNodesList.get(this.myNodesList.size()-2);
		if (parentNode.name.equals("title")) {
			return ETextAlign.CENTER;
		}
		return ETextAlign.JUSTIFY;
	}
}
