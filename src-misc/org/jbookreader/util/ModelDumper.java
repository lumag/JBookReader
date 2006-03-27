package org.jbookreader.util;

import java.io.PrintWriter;

import org.jbookreader.book.bom.IBook;
import org.jbookreader.book.bom.IContainerNode;
import org.jbookreader.book.bom.IImageNode;
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
		
		if (node instanceof IImageNode) {
			IImageNode image = (IImageNode) node;
			writer.format(" xlink:href=\"%s\"",  image.getHyperRef());
			if (image.getText() != null) {
				writer.format(" alt=\"%s\"", image.getText());
			}
			if (image.getTitle() != null) {
				writer.format(" title=\"%s\"", image.getTitle());
			}
		}

		if (!node.isContainer()) {
			writer.print(" />");
			return;
		}
		
		IContainerNode cnode = (IContainerNode)node;
		if (cnode.getChildNodes().isEmpty()) {
			writer.print(" />");
			return;
		}


		writer.println('>');
		
		for (INode child : cnode.getChildNodes()) {
			printNode(writer, child);
		}

		writer.println();
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
		writer.println("<FictionBook xmlns=\"http://www.gribuser.ru/xml/fictionbook/2.0\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">");
		for (IContainerNode node: book.getBodies())
			ModelDumper.printNode(writer, node);
		writer.println();
		writer.print("</FictionBook>");
	}
	
}
	