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
package org.jbookreader.book.parser;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * This class represents a handler for errors generated during parsing.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class ParseErrorHandler implements ErrorHandler {

	public void warning(SAXParseException exception) {
		System.out.println("Received a warning: '" + exception.getSystemId() +
				"' at line " + exception.getLineNumber() + " : " + exception.getMessage());
		exception.printStackTrace();
	}

	public void error(SAXParseException exception) throws SAXException {
		throw new SAXException("Error!", exception);
	}

	public void fatalError(SAXParseException exception) throws SAXException {
		throw new SAXException("Fatal error!", exception);
	}
	
}

