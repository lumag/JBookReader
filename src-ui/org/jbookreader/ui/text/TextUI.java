package org.jbookreader.ui.text;

import java.io.PrintWriter;

import org.jbookreader.book.bom.IBook;
import org.jbookreader.book.parser.FB2Parser;
import org.jbookreader.formatengine.FormatEngine;

/**
 * This class represents the simple text UI for book reading.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class TextUI {
	/**
	 * This is the main method. For text UI it's very simple, isn't it?
	 * @param args the name of the book to read.
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		IBook book = FB2Parser.parse(args.length>0?args[0]:"examples/simple.fb2");

		PrintWriter wr = new PrintWriter(System.out);
		TextPainter painter = new TextPainter(wr);

		FormatEngine engine = new FormatEngine();
		engine.setBook(book);
		engine.setPainter(painter);
		
		engine.renderPage();

		wr.close();
	}
}
