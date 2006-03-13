package org.jbookreader.ui;


import org.jbookreader.book.Book;
import org.jbookreader.book.bom.IContainerNode;
import org.jbookreader.book.bom.INode;
import org.jbookreader.book.parser.FB2Parser;

/**
 * This is a very simple application: it just parses the book and outputs the parsed tree as XML.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class BookParserExample {

	/**
	 * The recursive helper function for printing the tree node,
	 * @param node the node to print
	 */
	public static void printNode(INode node) {
		if (node.isContainer()) {
			System.out.print('<');
			System.out.print(node.getTagName());

			String id;
			if ((id = node.getID()) != null) {
				System.out.printf(" id=\"%s\"", id);
			}
			System.out.println('>');

			IContainerNode cnode = (IContainerNode)node;
			for (INode child : cnode.getChildNodes())
				printNode(child);
			System.out.println("</" + node.getTagName() + '>');
		} else {
			System.out.println(node.getText());
		}
	}

	/**
	 * Parse the provided book (or "examples/simple.fb2" if none given) and output
	 * the XML tree.
	 * @param args the name of the book
	 * @throws Exception in case of any error.
	 */
	public static void main(String[] args) throws Exception {
		Book book = FB2Parser.parse(args.length>0?args[0]:"examples/simple.fb2");
		for (INode node: book)
			printNode(node);
	}

}
