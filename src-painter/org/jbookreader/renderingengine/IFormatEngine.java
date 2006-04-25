package org.jbookreader.renderingengine;

import java.util.List;

import org.jbookreader.book.bom.INode;
import org.jbookreader.book.stylesheet.IStyleStack;
import org.jbookreader.formatengine.IBookPainter;
import org.jbookreader.formatengine.objects.Line;

public interface IFormatEngine {

	List<Line> formatParagraphNode(IBookPainter painter, INode node, IStyleStack styleStack, double width);

}