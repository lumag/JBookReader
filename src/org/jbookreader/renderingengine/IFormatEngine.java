package org.jbookreader.renderingengine;

import java.util.List;

import org.jbookreader.book.bom.INode;
import org.jbookreader.book.stylesheet.IStyleStack;
import org.jbookreader.formatengine.IBookPainter;
import org.jbookreader.formatengine.objects.Line;

/**
 * This interface specifies the formatting engine objects.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public interface IFormatEngine {

	/**
	 * Formats a single paragraph-like node.
	 * @param painter the painter to use for formatting.
	 * @param node the node to format
	 * @param styleStack the correspondig style stack
	 * @param width the width for the formatted paragraph
	 * @return the list of lines which contain formmated node. 
	 */
	List<Line> formatParagraphNode(IBookPainter painter, INode node, IStyleStack styleStack, double width);

}