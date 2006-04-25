package org.jbookreader.formatengine;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.jbookreader.book.bom.IBinaryData;
import org.jbookreader.book.bom.IContainerNode;
import org.jbookreader.book.bom.IImageNode;
import org.jbookreader.book.bom.INode;
import org.jbookreader.book.stylesheet.IStyleStack;
import org.jbookreader.formatengine.objects.HorizontalGlue;
import org.jbookreader.formatengine.objects.Line;
import org.jbookreader.formatengine.objects.MetaString;
import org.jbookreader.renderingengine.IFormatEngine;

/**
 * This class represents the core of the program: the text-formatting engine.
 * Each paragraph-level node is processed recursievly and each text node is
 * processed word-by-word. Words intermixed with horizontal glue objects are put
 * into lines. Then the list of lines is reprocessed and rendered with
 * corresponding {@link org.jbookreader.formatengine.IBookPainter} instance.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 * 
 */
public class FormatEngine implements IFormatEngine {

	/**
	 * Skip all whitespace in the provided string and return the the index of
	 * first non-whitespace char.
	 * 
	 * @param text the string.
	 * @param start the start of the string
	 * @return the index of first non-whitespace character.
	 */
	private int consumeWhitespace(String text, int start) {
		while ((start < text.length()) && (text.charAt(start) <= '\u0020'))
			start ++;
		return start;
	}
	
	private Line formatNode(List<Line> result, Line currentLine, INode node, IStyleStack styleStack, double width) {
		if (node instanceof IContainerNode) {
			return formatContainerNode(result, currentLine, (IContainerNode) node, styleStack, width);
		} else if (node instanceof IImageNode) {
			return formatImageNode(result, currentLine, (IImageNode) node, styleStack, width);
		} else {
			return formatTextNode(result, currentLine, node, styleStack, width);
		}
	}
	
	private Line formatContainerNode(List<Line> result, Line currentLine, IContainerNode cnode, IStyleStack styleStack, double width) {
		for (INode childNode : cnode.getChildNodes()) {
			styleStack.pushTag(childNode.getTagName(), childNode.getNodeClass(), childNode.getID());
			currentLine = formatNode(result, currentLine, childNode, styleStack, width);
			styleStack.popTag();
		}

		return currentLine;
	}

	private Line formatImageNode(List<Line> result, Line currentLine, IImageNode image, IStyleStack styleStack, double width) {
		// XXX: correct work with url's.
		IBinaryData blob = image.getBook().getBinaryData(image.getHyperRef().substring(1));
		IRenderingObject robject = currentLine.getPainter().getImage(null, blob.getContentType(), new ByteArrayInputStream(blob.getContentsArray(), 0, blob.getContentsLength()));

		if (robject == null) {
			return formatTextNode(result, currentLine, image, styleStack, width);
		}

		return appendRobject(result, currentLine, width, robject);
	}

	private Line formatTextNode(List<Line> result, Line currentLine, INode node, IStyleStack styleStack, double width) {
		String text = node.getText();

		if (text == null) {
			System.err.println("WARNING: node '" + node.getTagName() + "' doesn't has text");
			return currentLine;
		}

		IFont font = currentLine.getPainter().getFont(styleStack.getFontFamily(), styleStack.getFontSize());
		int start = 0;
		int end = 0;
		while (end < text.length()) {
			int newWord = consumeWhitespace(text, start);
			if (newWord > start) {
				// XXX: calculate more correct space size?
				double strut = font.getSpaceWidth();
				if (strut + currentLine.getWidth() <= width) {
					currentLine.addObject(new HorizontalGlue(currentLine.getPainter(), node, strut));
				}
			}
			end = start = newWord;

			if (end >= text.length())
				break;

			while ((end < text.length()) && (text.charAt(end) > '\u0020')) {
				end ++;
			}

			IRenderingObject string = new MetaString(currentLine.getPainter(), node, styleStack.getLineHeight(), text, start, end, font);
			currentLine = appendRobject(result, currentLine, width, string);

			start = end;
		}

		return currentLine;
	}
	
	private Line appendRobject (List<Line> result, Line currentLine, double width, IRenderingObject object) {
		// XXX: this is the main place for rendering decision
		if (currentLine.getWidth() + object.getWidth() > width) {
			// XXX: adjust glue objects in the line
			result.add(currentLine);
			double leftMargin = currentLine.getLeftMargin();
			currentLine = new Line(currentLine.getPainter(), currentLine.getNode());
			currentLine.setLeftMargin(leftMargin);
		}
		currentLine.addObject(object);
		return currentLine;
	}

	public List<Line> formatParagraphNode(IBookPainter painter, INode node, IStyleStack styleStack, double width) {
		List<Line> result = new ArrayList<Line>();

		Line cur = new Line(painter, node);
		cur.setLeftMargin(styleStack.getMarginLeft());
		cur.addObject(new HorizontalGlue(painter, node, styleStack.getTextIndent()));
		cur = formatNode(result, cur, node, styleStack, width);

		if (cur.getHeight() == 0
			&& cur.getWidth() != 0) {
			cur.addObject(new HorizontalGlue(painter, cur.getNode(), 0, styleStack.getLineHeight() * styleStack.getFontSize()));
		}
		result.add(cur);

		return result;
	}

}
