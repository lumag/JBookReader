package org.jbookreader.ui.text;

import org.jbookreader.book.Book;
import org.jbookreader.book.parser.FB2Parser;
import org.jbookreader.formatengine.FormatEngine;

public class TextUI {
	public static void main(String[] args) throws Exception {
		Book book = FB2Parser.parse(args.length>0?args[0]:"examples/simple.fb2");

		TextPainter painter = new TextPainter(System.out);

		FormatEngine engine = new FormatEngine();
		engine.setBook(book);
		engine.setPainter(painter);
		
		engine.renderPage();
	}
}
