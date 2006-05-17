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
package org.jbookreader.ui.text;

import java.io.PrintWriter;

import org.jbookreader.book.bom.IBook;
import org.jbookreader.book.parser.FB2Parser;
import org.jbookreader.formatengine.impl.FormatEngine;
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

		RenderingEngine engine = new RenderingEngine(new FormatEngine());
		engine.setBook(book);
		engine.setPainter(painter);
		
		engine.renderPage();

		wr.close();
	}
}
