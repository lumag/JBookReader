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
 * This interface represents a 'binary blob' part of the book.
 * It can be viewed in two representations: as a Base64-encoded string,
 * or as a byte array with specified length.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public interface IBinaryData {

	/**
	 * Returns the content-type of the blob.
	 * @return the content-type.
	 */
	String getContentType();

	/**
	 * Sets the content-type of the blob.
	 * @param contentType new content-type
	 */
	void setContentType(String contentType);
	
	/**
	 * Returns a character sequence iterating over base64-encoded
	 * representation of the binary data.
	 * @return a character sequence iterating over base64-encoded
	 * representation of the binary data.
	 */
	CharSequence getBase64Encoded();

	/**
	 * Sets the contents of the blob by providing base64-encoding
	 * representation of data.
	 * @param base64Encoded the base64-encoded representation of data
	 */
	void setBase64Encoded(char[] base64Encoded);

	/**
	 * Sets the contents by providing a byte array and a length.
	 * @param contents the new contents array.
	 * @param length the length of contents.
	 */
	void setContents(byte[] contents, int length);

	/**
	 * Returns the contents array of the data.
	 * Note, that data can fill not the whole array, but the part of it.
	 * @return the contents array.
	 * @see #getContentsLength()
	 */
	byte[] getContentsArray();

	/**
	 * Returns the contents length
	 * @return the length of the contents in the contents array.
	 */
	int getContentsLength();

}