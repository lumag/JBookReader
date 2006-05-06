package org.jbookreader.formatengine.impl;

import java.io.ByteArrayInputStream;

import org.jbookreader.book.bom.IBinaryData;
import org.jbookreader.book.bom.IContainerNode;
import org.jbookreader.book.bom.IImageNode;
import org.jbookreader.book.bom.INode;
import org.jbookreader.book.stylesheet.IStyleStack;
import org.jbookreader.formatengine.IBookPainter;
import org.jbookreader.formatengine.IFont;
import org.jbookreader.formatengine.IFormatEngine;
import org.jbookreader.formatengine.IInlineRenderingObject;
import org.jbookreader.formatengine.IRenderingObject;
import org.jbookreader.formatengine.objects.HorizontalGlue;
import org.jbookreader.formatengine.objects.Line;
import org.jbookreader.formatengine.objects.MetaString;
import org.jbookreader.formatengine.objects.VerticalIROStack;

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

	private IRenderingObject myResult;
	private VerticalIROStack myCurrentParagraph;
	private Line myCurrentLine;

	/**
	 * Skip all whitespace in the provided string and return the the index of
	 * first non-whitespace char.
	 * 
	 * @param text the string.
	 * @param start the start of the string
	 * @return the index of first non-whitespace character.
	 */
	private int consumeWhitespace(String text, int start) {
		while ((start < text.length()) && (text.charAt(start) <= '\u0020')) {
			start ++;
		}
		return start;
	}
	
	private void formatNode(INode node, IStyleStack styleStack, double width) {
		if (node instanceof IContainerNode) {
			formatContainerNode((IContainerNode) node, styleStack, width);
		} else if (node instanceof IImageNode) {
			formatInlineImageNode((IImageNode) node, styleStack, width);
		} else {
			formatTextNode(node, styleStack, width);
		}
	}
	
	private void formatContainerNode(IContainerNode cnode, IStyleStack styleStack, double width) {
		for (INode childNode : cnode.getChildNodes()) {
			styleStack.pushTag(childNode.getTagName(), childNode.getNodeClass(), childNode.getID());
			formatNode(childNode, styleStack, width);
			styleStack.popTag();
		}
	}
	
	private IInlineRenderingObject getImageObject(IBookPainter painter, IImageNode image) {
		// XXX: correct work with url's.
		IBinaryData blob = image.getBook().getBinaryData(image.getHyperRef().substring(1));
		IInlineRenderingObject robject = painter.getImage(
				null,
				blob.getContentType(),
				new ByteArrayInputStream(
						blob.getContentsArray(),
						0,
						blob.getContentsLength()
						)
				);
		
		return robject;
	}

	private void formatInlineImageNode(IImageNode image, IStyleStack styleStack, double width) {
		
		IInlineRenderingObject robject = getImageObject(this.myCurrentLine.getPainter(), image);

		if (robject == null) {
			formatTextNode(image, styleStack, width);
		} else {
			appendRobject(width, robject);
		}
	}

	private void formatImageNode(IBookPainter painter, IImageNode image, IStyleStack styleStack, double width) {
		IRenderingObject robject = getImageObject(painter, image);

		if (robject == null) {
			newParagraph(painter, image, styleStack);
			width -= styleStack.getMarginLeft() + styleStack.getMarginRight();

			formatTextNode(image, styleStack, width);

			this.myCurrentParagraph.addObject(this.myCurrentLine);
			this.myCurrentLine = null;
		} else {
			// TODO: format title, etc.
			this.myResult = robject;
		}
	}

	private void formatTextNode(INode node, IStyleStack styleStack, double width) {
		String text = node.getText();

		if (text == null) {
			System.err.println("WARNING: node '" + node.getTagName() + "' doesn't has text");
			return;
		}

		IFont font = this.myCurrentLine.getPainter().getFont(styleStack.getFontFamily(), styleStack.getFontSize());
		int start = 0;
		int end = 0;
		while (end < text.length()) {
			int newWord = consumeWhitespace(text, start);
			if (newWord > start) {
				// XXX: calculate more correct space size?
				double strut = font.getSpaceWidth();
				if (strut + this.myCurrentLine.getWidth() <= width) {
					this.myCurrentLine.addObject(new HorizontalGlue(this.myCurrentLine.getPainter(), node, strut));
				}
			}
			end = start = newWord;

			if (end >= text.length()) {
				break;
			}

			while ((end < text.length()) && (text.charAt(end) > '\u0020')) {
				end ++;
			}

			IInlineRenderingObject string = new MetaString(this.myCurrentLine.getPainter(), node, styleStack.getLineHeight(), text, start, end, font);
			appendRobject(width, string);

			start = end;
		}
	}
	
	private void appendRobject (double width, IInlineRenderingObject object) {
		// XXX: this is the main place for rendering decision
		if (this.myCurrentLine.getWidth() + object.getWidth() > width) {
			// XXX: adjust glue objects in the line
			this.myCurrentParagraph.addObject(this.myCurrentLine);
			this.myCurrentLine = new Line(this.myCurrentLine.getPainter(), this.myCurrentLine.getNode());
		}
		this.myCurrentLine.addObject(object);
	}
	
	private void newParagraph(IBookPainter painter, INode node, IStyleStack styleStack) {
		this.myResult = this.myCurrentParagraph = new VerticalIROStack(painter, node);

		this.myCurrentParagraph.setMargins(
				styleStack.getMarginTop(),
				styleStack.getMarginRight(),
				styleStack.getMarginBottom(),
				styleStack.getMarginLeft()
				);
		
		this.myCurrentLine = new Line(painter, node);
		this.myCurrentLine.addObject(new HorizontalGlue(painter, node, styleStack.getTextIndent()));
	}

	private void formatStyledText(IBookPainter painter, INode node, IStyleStack styleStack, double width) {
		newParagraph(painter, node, styleStack);
		width -= styleStack.getMarginLeft() + styleStack.getMarginRight();

		formatNode(node, styleStack, width);

		if (this.myCurrentLine.getHeight() == 0
			&& this.myCurrentLine.getWidth() != 0) {
			this.myCurrentLine.addObject(
					new HorizontalGlue(
							painter,
							this.myCurrentLine.getNode(),
							0,
							styleStack.getLineHeight() * styleStack.getFontSize()
							)
					);
		}
		this.myCurrentParagraph.addObject(this.myCurrentLine);
		this.myCurrentLine = null;
	}

	public IRenderingObject formatParagraphNode(IBookPainter painter, INode node, IStyleStack styleStack, double width) {
		
		if (node instanceof IImageNode) {
			formatImageNode(painter, (IImageNode) node, styleStack, width);
		} else {
			formatStyledText(painter, node, styleStack, width);
		}

		return this.myResult;
	}

}
