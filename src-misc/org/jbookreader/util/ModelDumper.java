package org.jbookreader.util;

import java.io.PrintWriter;

import org.jbookreader.book.bom.IBook;
import org.jbookreader.book.bom.IContainerNode;
import org.jbookreader.book.bom.INode;

/**
 * This is utility class for dumping BOM.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class ModelDumper {
	/**
	 * The recursive helper function for printing the tree node,
	 * @param writer the stream to output nodes to
	 * @param node the node to print
	 */
	private static void printNode(PrintWriter writer, INode node) {
		if (node == null)
			return;

		if (node.getTagName().equals("#text")) {
			writer.print(node.getText());
			return;
		}

		writer.print('<');
		writer.print(node.getTagName());

		String id;
		if ((id = node.getID()) != null) {
			writer.printf(" id=\"%s\"", id);
		}
		writer.println('>');
		
		if (node.isContainer()) {
			IContainerNode cnode = (IContainerNode)node;
			
			for (INode child : cnode.getChildNodes())
				printNode(writer, child);
			writer.println();
		}

		writer.print("</");
		writer.print(node.getTagName());
		writer.print( '>');
	}

	/**
	 * This outputs BOM to the stream in the XML format.
	 * 
	 * @param writer the stream to output book to
	 * @param book the book to dump
	 */
	public static void dumpBOM(PrintWriter writer, IBook book) {
		writer.println("<FictionBook>");
		for (INode node: book.getBodies())
			ModelDumper.printNode(writer, node);
		writer.println();
		writer.print("</FictionBook>");
	}
	
}
	