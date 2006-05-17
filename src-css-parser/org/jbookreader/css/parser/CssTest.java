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
package org.jbookreader.css.parser;

import org.w3c.css.sac.*;
import org.w3c.css.sac.helpers.ParserFactory;

/**
 * The demostration for the SAC api:
 * parse and dump css files.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class CssTest {
	/**
	 * The 'main' method of the demostration.
	 * 
	 * @param args the list with CSS file names.
	 * @throws Exception because it's a demo, we don't handle exceptions.
	 */
	public static void main(String[] args) throws Exception {
		if (System.getProperty("org.w3c.css.sac.parser") == null) {
			System.setProperty("org.w3c.css.sac.parser", "org.w3c.flute.parser.Parser");
		}

		Parser cssParser = new ParserFactory().makeParser();

		cssParser.setErrorHandler(new CssErrorHandler());
		cssParser.setDocumentHandler(new CssDemoHandler());

		System.out.println("Parser version: " + cssParser.getParserVersion());

		if (args.length == 0) {
			System.err.println("Usage: java CssTest <file.css>...");
		} else {
			for (String uri: args) {
				System.out.format("Parsing: '%s'%n", uri);
				cssParser.parseStyleSheet(uri);
			}
		}
	}
}
