package org.jbookreader.css.parser;

import org.w3c.css.sac.*;

public class CssErrorHandler implements ErrorHandler {
	public void error(CSSParseException exception) {
		System.err.println("Error");
		exception.printStackTrace();
	}

	public void fatalError(CSSParseException exception) throws CSSException{
		System.err.println("fatalError");
		throw exception;
	}

	public void warning(CSSParseException exception) {
		System.err.println("Warning");
		exception.printStackTrace();
	}
}