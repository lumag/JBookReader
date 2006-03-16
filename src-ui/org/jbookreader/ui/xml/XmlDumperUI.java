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
