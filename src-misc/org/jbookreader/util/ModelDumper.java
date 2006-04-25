package org.jbookreader.util;

import java.io.PrintWriter;
import java.util.Map;

import org.jbookreader.book.bom.IBinaryData;
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
	private static final String TEXT_NODE_NAME = "#text"; //$NON-NLS-1$

	/**
	 * The recursive helper function for printing the tree node,
	 * @param writer the stream to output nodes to
	 * @param node the node to print
	 */
	private static void printNode(PrintWriter writer, INode node) {
		if (node == null)
			return;
		
		if (node.getTagName().equals(TEXT_NODE_NAME)) {
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
		
		if (node.getNodeClass() != null) {
			String nodeClass = node.getNodeClass();
			String tag = node.getTagName();
			String classAttribute = "class";
			if (tag.equals("p")) {
				classAttribute = "style";
			} else if (tag.equals("style")) {
				classAttribute = "name";
			}
			writer.format(" %s=\"%s\"", classAttribute, nodeClass);
		}
		
		IContainerNode cnode = null;
		if (node instanceof IContainerNode) {
			cnode = (IContainerNode)node;
		}

		if ((cnode == null) || cnode.getChildNodes().isEmpty()) {
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
		for (Map.Entry<String, ? extends IBinaryData> binaryEntry: book.getBinaryMap().entrySet()) {
			writer.format("<binary id=\"%s\" content-type=\"%s\">%n", binaryEntry.getKey(), binaryEntry.getValue().getContentType());
			writer.append(binaryEntry.getValue().getBase64Encoded());
			writer.println("</binary>");
		}
		writer.print("</FictionBook>");
	}
	
}
	