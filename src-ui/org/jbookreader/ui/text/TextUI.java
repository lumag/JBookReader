package org.jbookreader.ui.text;

import org.jbookreader.book.bom.IBook;
import org.jbookreader.book.parser.FB2Parser;
import org.jbookreader.formatengine.FormatEngine;

public class TextUI {
	public static void main(String[] args) throws Exception {
		IBook book = FB2Parser.parse(args.length>0?args[0]:"examples/simple.fb2");

		TextPainter painter = new TextPainter(System.out);

		FormatEngine engine = new FormatEngine();
		engine.setBook(book);
		engine.setPainter(painter);
		
		engine.renderPage();
	}
}
