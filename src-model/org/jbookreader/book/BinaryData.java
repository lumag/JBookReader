package org.jbookreader.book;

/**
 * This class represents a single binary data blob. It can be viewed
 * in two representations: as a Base64-encoded string, or as a byte
 * array and a length.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class BinaryData {
	/**
	 * The id of the blob.
	 */
	private String myID;
	/**
	 * Content-Type of the blob.
	 */
	private String myContentType;
	
	/**
	 * Contents array.
	 */
	private byte[] myContentsArray = new byte[1];
	/**
	 * The length of the contents.
	 */
	private int myContentsLength;
	
	/**
	 * Returns the content-type of the blob.
	 * @return the content-type.
	 */
	public String getContentType() {
		return this.myContentType;
	}
	/**
	 * Sets the content-type of the blob.
	 * @param contentType new content-type
	 */
	public void setContentType(String contentType) {
		this.myContentType = contentType;
	}
	/**
	 * Returns the ID of the blob.
	 * @return the ID of the blob.
	 */
	public String getID() {
		return this.myID;
	}
	/**
	 * Sets the ID of the blob.
	 * @param id new ID
	 */
	public void setID(String id) {
		this.myID = id;
	}

	/**
	 * Sets the contents of the blob by providing base64-encoding
	 * representation of data.
	 * @param base64Encoded
	 */
	public void setBase64Encoded(char[] base64Encoded) {
		this.myContentsArray = new byte[base64Encoded.length/4*3];
		this.myContentsLength = 0;
		long bitBuffer = 0;
		int bitBufferBytes = 0;
		int byteBufferPad = 0;

		for (int i = 0; i < base64Encoded.length; i++) {
			char curch = base64Encoded[i]; 
			if (curch == '\n' || curch == '\r') {
				continue;
			}
			bitBuffer <<= 6;
			if (curch >= 'A' && curch <= 'Z') {
				bitBuffer += (curch - 'A');
			} else if (curch >= 'a' && curch <= 'z') {
				bitBuffer +=  (curch - 'a' + 26);
			} else if (curch >= '0' && curch <= '9') {
				bitBuffer += (curch - '0' + 52);
			} else if (curch == '+') {
				bitBuffer += 62;
			} else if (curch == '/') {
				bitBuffer += 63;
			} else if (curch == '=') {
				byteBufferPad ++;
			} else {
				throw new IllegalArgumentException("Bad character value: '" + curch + "'");
			}
			bitBufferBytes ++;
			if (bitBufferBytes == 4) {
				this.myContentsArray[this.myContentsLength ++] = (byte)(bitBuffer >> 16);
				if (byteBufferPad < 2) {
					this.myContentsArray[this.myContentsLength ++] = (byte)(bitBuffer >> 8);
				}
				if (byteBufferPad < 1) {
					this.myContentsArray[this.myContentsLength ++] = (byte)(bitBuffer >> 0);
				}
				bitBufferBytes = 0;
				bitBuffer = 0;
			}
		}
	}

	/**
	 * Sets the contents by providing a byte array and a length.
	 * @param contents the new contents array.
	 * @param length the length of contents.
	 */
	public void setContents(byte[] contents, int length) {
		if (this.myContentsLength < length) {
			this.myContentsArray = new byte[length];
		}
		this.myContentsLength = length;
		for (int i = 0; i < length; i++) {
			this.myContentsArray[i] = contents[i];
		}
	}

	/**
	 * Returns the contents array of the data.
	 * Note, that data can fill not the whole array, but the part of it.
	 * @return the contents array.
	 * @see #getContentsLength()
	 */
	public byte[] getContentsArray() {
		return this.myContentsArray;
	}
	/**
	 * Returns the contents length
	 * @return the length of the contents in the contents array.
	 */
	public int getContentsLength() {
		return this.myContentsLength;
	}
}
