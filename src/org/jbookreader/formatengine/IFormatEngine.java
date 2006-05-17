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


import java.util.List;

import org.jbookreader.book.bom.INode;
import org.jbookreader.book.stylesheet.IStyleStack;

/**
 * This interface specifies the formatting engine objects.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public interface IFormatEngine {

	/**
	 * Formats a single paragraph-like node.
	 * @param painter the painter to use for formatting.
	 * @param node the node to format
	 * @param styleStack the correspondig style stack
	 * @param width the width for the formatted paragraph
	 * @return list of formmated objects for paragraph node. 
	 */
	List<IRenderingObject> formatParagraphNode(IBookPainter painter, INode node, IStyleStack styleStack, double width);

}