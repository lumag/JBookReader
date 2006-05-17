/*
 * JBookReader - Java FictionBook Reader
 * Copyright (C) 2006 Dmitry Baryshkov
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *   
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *   
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the Free Software
 *   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.jbookreader.formatengine.impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

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

	private List<IRenderingObject> myResult;
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
			appendRobject(robject, styleStack, width);
		}
	}

	private void formatImageNode(IBookPainter painter, IImageNode image, IStyleStack styleStack, double width) {
		IRenderingObject robject = getImageObject(painter, image);

		if (robject == null) {
			newParagraph(painter, image, styleStack);
			width -= styleStack.getMarginLeft() + styleStack.getMarginRight();

			formatTextNode(image, styleStack, width);

			this.myResult.add(this.myCurrentLine);
			this.myCurrentLine = null;
		} else {
			// TODO: format title, etc.
			this.myResult.add(robject);
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
					this.myCurrentLine.appendObject(new HorizontalGlue(this.myCurrentLine.getPainter(), node, strut));
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
			appendRobject(string, styleStack, width);

			start = end;
		}
	}

	private void flushLine(boolean lastLine, double width, IStyleStack styleStack) {
		switch (styleStack.getTextAlign()) {
			case LEFT:
				this.myCurrentLine.appendObject(new HorizontalGlue(this.myCurrentLine.getPainter(), this.myCurrentLine.getNode(), width - this.myCurrentLine.getWidth()));
				break;
			case RIGHT:
				this.myCurrentLine.prependObject(new HorizontalGlue(this.myCurrentLine.getPainter(), this.myCurrentLine.getNode(), width - this.myCurrentLine.getWidth()));
				break;
			case CENTER:
				this.myCurrentLine.prependObject(new HorizontalGlue(this.myCurrentLine.getPainter(), this.myCurrentLine.getNode(), (width - this.myCurrentLine.getWidth())/2));
				this.myCurrentLine.appendObject(new HorizontalGlue(this.myCurrentLine.getPainter(), this.myCurrentLine.getNode(), width - this.myCurrentLine.getWidth()));
				break;
			case JUSTIFY:
				if (!lastLine) {
					this.myCurrentLine.adjustWidth(width - this.myCurrentLine.getWidth());
				}
		}
		this.myResult.add(this.myCurrentLine);
		this.myCurrentLine = null;
	}
	
	private void appendRobject (IInlineRenderingObject object, IStyleStack styleStack, double width) {
		// XXX: this is the main place for rendering decision
		if (this.myCurrentLine.getWidth() + object.getWidth() > width) {
			Line newLine = new Line(this.myCurrentLine.getPainter(), this.myCurrentLine.getNode());
			flushLine(false, width, styleStack);
			this.myCurrentLine = newLine;
		}
		this.myCurrentLine.appendObject(object);
	}
	
	private void newParagraph(IBookPainter painter, INode node, IStyleStack styleStack) {
		this.myResult = new ArrayList<IRenderingObject>();

		this.myCurrentLine = new Line(painter, node);
		this.myCurrentLine.appendObject(new HorizontalGlue(painter, node, styleStack.getTextIndent()));
	}

	private void formatStyledText(IBookPainter painter, INode node, IStyleStack styleStack, double width) {
		newParagraph(painter, node, styleStack);
		width -= styleStack.getMarginLeft() + styleStack.getMarginRight();

		formatNode(node, styleStack, width);

		// XXX: hack to fix emty-line rendering
		if (this.myCurrentLine.getHeight() == 0
			&& this.myCurrentLine.getWidth() == 0) {
			this.myCurrentLine.appendObject(
					new HorizontalGlue(
							painter,
							this.myCurrentLine.getNode(),
							0,
							styleStack.getLineHeight() * styleStack.getFontSize()
							)
					);
		}
		flushLine(true, width, styleStack);
	}

	public List<IRenderingObject> formatParagraphNode(IBookPainter painter, INode node, IStyleStack styleStack, double width) {
		
		this.myResult = new ArrayList<IRenderingObject>();
		
		if (node instanceof IImageNode) {
			formatImageNode(painter, (IImageNode) node, styleStack, width);
		} else {
			formatStyledText(painter, node, styleStack, width);
		}
		
		this.myResult.get(0).setMarginTop(styleStack.getMarginTop());
		this.myResult.get(this.myResult.size()-1).setMarginBottom(styleStack.getMarginBottom());
		
		for (IRenderingObject robject: this.myResult) {
			robject.setMarginLeft(styleStack.getMarginLeft());
			robject.setMarginRight(styleStack.getMarginRight());
		}
		
		return this.myResult;
	}

}
