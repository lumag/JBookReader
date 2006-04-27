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

