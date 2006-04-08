package org.jbookreader.css.parser;

import org.w3c.css.sac.*;
import org.w3c.css.sac.helpers.ParserFactory;

public class CssTest {
	public static void main(String[] args) throws Exception {
		if (System.getProperty("org.w3c.css.sac.parser") == null) {
			System.setProperty("org.w3c.css.sac.parser", "org.w3c.flute.parser.Parser");
		}

		Parser cssParser = new ParserFactory().makeParser();

		cssParser.setErrorHandler(new CssErrorHandler());
		cssParser.setDocumentHandler(new CssHandler());

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
