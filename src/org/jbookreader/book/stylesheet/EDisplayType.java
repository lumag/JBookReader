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
 * This enum contains values corresponding to the 'display' CSS property.
 * They control how the display of the node is handled and displayed on the
 * canvas during display.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 * XXX: add if necessary list-item and table-related ones?
 */
public enum EDisplayType {
	/**
	 * A new box.
	 */
	BLOCK,
	/**
	 * New box on the same line, as the previous context.
	 */
	INLINE,
	/**
	 * Not displayed. Child items aren't displayed too.
	 */
	NONE;
}
