package org.jbookreader;

import org.jbookreader.book.Book;
import org.jbookreader.book.bom.IContainerNode;
import org.jbookreader.book.bom.INode;
import org.jbookreader.book.parser.FB2Parser;

public class BookParserExample {

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
	
	public static void main(String[] args) throws Exception {
		Book book = FB2Parser.parse(args.length>0?args[0]:"examples/simple.fb2");
		for (INode node: book)
			printNode(node);
	}

}
