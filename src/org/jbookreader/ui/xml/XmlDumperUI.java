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
package org.jbookreader.ui.xml;


import java.io.PrintWriter;

import org.jbookreader.book.bom.IBook;
import org.jbookreader.book.parser.FB2Parser;
import org.jbookreader.util.ModelDumper;

/**
 * This is a very simple application: it just parses the book and outputs the parsed tree as XML.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class XmlDumperUI {

	/**
	 * Parse the provided book (or "examples/simple.fb2" if none given) and output
	 * the XML tree.
	 * @param args the name of the book
	 * @throws Exception in case of any error.
	 */
	public static void main(String[] args) throws Exception {
		IBook book = FB2Parser.parse(args.length>0?args[0]:"examples/simple.fb2");
		PrintWriter pwr = new PrintWriter(System.out);
		ModelDumper.dumpBOM(pwr, book);
		pwr.close();
	}

}
