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

import java.util.List;


/**
 * This interface represents a container node.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public interface IContainerNode extends INode {

	/**
	 * Returns a collection of child (contained in this one) nodes.
	 * @return a collection of child nodes.
	 */
	List<INode> getChildNodes();

	/**
	 * This creates new child text node with specified text.
	 * @param text the text of created node
	 * @return new text node.
	 */
	INode newTextNode(String text);
	/**
	 * Creates new child container node.
	 * @param tagName the tag name of new node
	 * @return new container node.
	 */
	IContainerNode newContainerNode(String tagName);

	/**
	 * Returns the title of the section.
	 * @return the title of the section.
	 */
	IContainerNode getTitle();
	/**
	 * Allocates the title of the section.
	 * @param tagName the tag name of the title node
	 * @return newly created container node.
	 */
	IContainerNode newTitle(String tagName);
	
	/**
	 * Allocates new Image node.
	 * @param tagName the tag name of the image node
	 * @param href the url of the image 
	 * @return newly created image node.
	 */
	IImageNode newImage(String tagName, String href);

}
