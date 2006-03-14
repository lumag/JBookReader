package org.jbookreader.book.bom.impl;

import org.jbookreader.book.bom.IBinaryData;

/**
 * This class represents a single binary data blob. It can be viewed
 * in two representations: as a Base64-encoded string, or as a byte
 * array with specified length.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
class BinaryData implements IBinaryData {
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
	
	public String getContentType() {
		return this.myContentType;
	}

	public void setContentType(String contentType) {
		this.myContentType = contentType;
	}

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

	public void setContents(byte[] contents, int length) {
		if (this.myContentsLength < length) {
			this.myContentsArray = new byte[length];
		}
		this.myContentsLength = length;
		for (int i = 0; i < length; i++) {
			this.myContentsArray[i] = contents[i];
		}
	}

	public byte[] getContentsArray() {
		return this.myContentsArray;
	}

	public int getContentsLength() {
		return this.myContentsLength;
	}
}
