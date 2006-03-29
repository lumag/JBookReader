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

	private char getChar(long num) {
		if (num < 0 || num > 63) {
			throw new IllegalArgumentException("Bad character number during encoding: " + num);
		} else if (num < 26) {
			return (char) ('A' + num);
		} else if (num < 52) {
			return (char) ('a' + num - 26);
		} else if (num < 62) {
			return (char) ('0' + num - 52);
		} else if (num == 62) {
			return '+';
		} else { // 63 
			return '/' ;
		}
	}
	
	public CharSequence getBase64Encoded() {
		StringBuilder result = new StringBuilder();
		int bitbuffer = 0;
		for (int i = 0; i < this.myContentsLength; i++) {
			bitbuffer = (bitbuffer << 8) | (this.myContentsArray[i] & 0xff);

			switch (i%3) {
				case 0:
					result.append(getChar((bitbuffer >>> 2) & 0x3F));
					break;
				case 1:
					result.append(getChar((bitbuffer >>> 4) & 0x3F));
					break;
				case 2:
					result.append(getChar((bitbuffer >>> 6) & 0x3F));
					result.append(getChar((bitbuffer >>> 0) & 0x3F));
					break;

			}

			if ((i + 1) % 54 == 0) { // 72 /4 * 3
				result.append("\n");
			}
		}

		if (this.myContentsLength % 3 == 1) {
			bitbuffer <<= 8;
			result.append(getChar((bitbuffer >>> 4) & 0x3F));
			result.append("==");
		} else if (this.myContentsLength % 3 == 2) {
			bitbuffer <<= 8;
			result.append(getChar((bitbuffer >>> 6) & 0x3F));
			result.append("=");
		}

		if (this.myContentsLength % 52 != 0) {
			result.append("\n");
		}

		return result;
	}

	public void setBase64Encoded(char[] base64Encoded) {
		this.myContentsArray = new byte[base64Encoded.length/4*3];
		this.myContentsLength = 0;
		int bitBuffer = 0;
		int bitBufferBytes = 0;
		int byteBufferPad = 0;

		for (int i = 0; i < base64Encoded.length; i++) {
			char curch = base64Encoded[i]; 
			if (curch == '\n' || curch == '\r' || curch == ' ' || curch == '\t') {
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
