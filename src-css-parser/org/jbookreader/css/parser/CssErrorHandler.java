package org.jbookreader.css.parser;

import org.w3c.css.sac.*;

/**
 * A handler for errors generated during CSS parsing.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
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
