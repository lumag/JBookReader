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
	 * Sets the contents of the blob by providing base64-encoding
	 * representation of data.
	 * @param base64Encoded
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