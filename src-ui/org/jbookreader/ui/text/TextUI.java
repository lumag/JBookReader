package org.jbookreader.ui.text;

import java.io.PrintWriter;

import org.jbookreader.book.bom.IBook;
import org.jbookreader.book.parser.FB2Parser;
import org.jbookreader.renderingengine.RenderingEngine;
import org.jbookreader.util.TextPainter;

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
		IBook book = FB2Parser.parse(args.length>0?args[0]:"tests/simple.fb2");

		PrintWriter wr = new PrintWriter(System.out);
		TextPainter painter = new TextPainter(wr, args.length > 1?Integer.parseInt(args[1]): 80);

		RenderingEngine engine = new RenderingEngine();
		engine.setBook(book);
		engine.setPainter(painter);
		
		engine.renderPage(false);

		wr.close();
	}
}
