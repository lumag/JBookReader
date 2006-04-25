package org.jbookreader.formatengine;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.jbookreader.book.bom.IBinaryData;
import org.jbookreader.book.bom.IBook;
import org.jbookreader.book.bom.IContainerNode;
import org.jbookreader.book.bom.IImageNode;
import org.jbookreader.book.bom.INode;
import org.jbookreader.book.stylesheet.IStyleStack;
import org.jbookreader.book.stylesheet.properties.EDisplayType;
import org.jbookreader.formatengine.model.HorizontalGlue;
import org.jbookreader.formatengine.model.Line;
import org.jbookreader.formatengine.model.MetaString;
import org.jbookreader.formatengine.model.VerticalGlue;

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
public class FormatEngine {
	/**
	 * Current book painter.
	 */
	private IBookPainter myPainter;

	/**
	 * Current book.
	 */
	private IBook myBook;

	/**
	 * Sets <code>painter</code> as a new output device.
	 * 
	 * @param painter painter representing new output device.
	 */
	public void setPainter(IBookPainter painter) {
		this.myPainter = painter;
	}

	/**
	 * Sets the new book to be output.
	 * 
	 * @param book new book to display.
	 */
	public void setBook(IBook book) {
		this.myBook = book;
		this.myStartLine = 0;
		this.myLines.clear();
	}

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
	
	// FIXME: remove!!!
	private Line recursiveFormatNode(List<Line> result, Line currentLine, INode node, IStyleStack styleStack, double width) {
//		if (true) {
//			throw new RuntimeException("Shouldn't use this");
//		}

		if (node instanceof IContainerNode) {
			IContainerNode cnode = (IContainerNode) node;

			for (INode childNode : cnode.getChildNodes()) {
				styleStack.pushTag(childNode.getTagName(), childNode.getNodeClass(), childNode.getID());
				currentLine = recursiveFormatNode(result, currentLine, childNode, styleStack, width);
				styleStack.popTag();
			}

			return currentLine;
		}

		if (node instanceof IImageNode) {
			IImageNode image = (IImageNode) node;
			// XXX: correct work with url's.
			IBinaryData blob = this.myBook.getBinaryData(image.getHyperRef().substring(1));
			IRenderingObject robject = this.myPainter.getImage(null, blob.getContentType(), new ByteArrayInputStream(blob.getContentsArray(), 0, blob.getContentsLength()));
			if (robject != null) {
				return appendRobject(result, currentLine, width, robject);
			}
		}

		String text = node.getText();

		if (text == null) {
			System.err.println("WARNING: node '" + node.getTagName() + "' doesn't has text");
			return currentLine;
		}

		IFont font = this.myPainter.getFont(styleStack.getFontFamily(), styleStack.getFontSize());
		int start = 0;
		int end = 0;
		while (end < text.length()) {
			int newWord = consumeWhitespace(text, start);
			if (newWord > start) {
				// XXX: calculate more correct space size?
				double strut = font.getSpaceWidth();
				if (strut + currentLine.getWidth() <= width) {
					currentLine.addObject(new HorizontalGlue(this.myPainter, node, strut));
				}
			}
			end = start = newWord;

			if (end >= text.length())
				break;

			while ((end < text.length()) && (text.charAt(end) > '\u0020')) {
				end ++;
			}

			IRenderingObject string = new MetaString(this.myPainter, node, styleStack, text, start, end, font);
			currentLine = appendRobject(result, currentLine, width, string);

			start = end;
		}

		return currentLine;
	}
	
	private Line appendRobject (List<Line> result, Line currentLine, double width, IRenderingObject object) {
		// XXX: this is the main place for rendering decision
		if (currentLine.getWidth() + object.getWidth() > width && !currentLine.getObjects().isEmpty()) {
			// XXX: adjust glue objects in the line
			result.add(currentLine);
			double leftMargin = currentLine.getLeftMargin();
			currentLine = new Line(this.myPainter, currentLine.getNode());
			currentLine.setLeftMargin(leftMargin);
		}
		currentLine.addObject(object);
		return currentLine;
	}

	private List<Line> formatNode(INode node, IStyleStack styleStack, double width) {
		List<Line> result = new ArrayList<Line>();

		Line cur = new Line(this.myPainter, node);
		cur.setLeftMargin(styleStack.getMarginLeft());
		cur.addObject(new HorizontalGlue(this.myPainter, node, styleStack.getTextIndent()));
		cur = recursiveFormatNode(result, cur, node, styleStack, width);

		if (cur.getHeight() == 0
			&& cur.getWidth() != 0) {
			cur.addObject(new VerticalGlue(this.myPainter, cur.getNode(), styleStack.getLineHeight()));
		}
		result.add(cur);

		return result;
	}

	/**
	 * The list with formatted lines.
	 */
	private List<Line> myLines = new ArrayList<Line>();

	/**
	 * The index of the line at the top of the displaying screen.
	 */
	private int myStartLine;

	/**
	 * The index of the line that shold start next page. Or <code>-1</code> if
	 * this line isn't calculated yet.
	 */
	private int myNextPageLine = -1;

	/**
	 * Returns the paragraph node to be formatted right before or after
	 * <code>node</code>
	 * 
	 * @param node current node
	 * @param styleStack the stack of style information corresponding to the
	 * <code>node</code>
	 * @param next whether to search next or previous node
	 * @return next paragraph node.
	 */
	private INode getParagraphNode(INode node, IStyleStack styleStack, boolean next) {
		while (true) {
			IContainerNode pnode = node.getParentNode();

			// end of book
			if (pnode == null) {
				return null;
			}

			List<INode> children = pnode.getChildNodes();
			int index = children.indexOf(node);
			int nextindex = index + ((next) ? (+1) : (-1));
			if (index == -1) {
				throw new IllegalStateException("Node '" + node + "' not found in it's parent list!!!!");
			} else if (nextindex < children.size() && nextindex >= 0) {
				node = children.get(nextindex);
				break;
			}
			node = pnode;
		}

		return getParagraphNodeDown(node, styleStack, next);
	}

	/**
	 * Returns the first or the last paragraph node in the specified container
	 * node.
	 * 
	 * @param node the container node.
	 * @param styleStack the stack of style information corresponding to the
	 * <code>node</code>
	 * @param first whether to search first or last node
	 * @return the first paragraph node in the specified container node.
	 */
	private INode getParagraphNodeDown(INode node, IStyleStack styleStack, boolean first) {
		while (true) {
			if (! (node instanceof IContainerNode)) {
				return node;
			}
			IContainerNode cnode = (IContainerNode)node;

			List<INode> children = cnode.getChildNodes();
			if (children.isEmpty())
				return node;

			int number = (first) ? 0 : (children.size() - 1);

			INode child = children.get(number);
			styleStack.pushTag(child.getTagName(), child.getNodeClass(), child.getID());
			if (styleStack.getDisplay() == EDisplayType.INLINE) {
				styleStack.popTag();
				return node;
			}

			node = child;
		}
	}

	/**
	 * Renders a page of text.
	 * 
	 * @param reformat whether we could use cached preformatted lines from
	 * previous calls.
	 */
	public void renderPage(boolean reformat) {
		INode node = null;
		IStyleStack styleStack;

		this.myPainter.clear();

		if (this.myLines.size() != 0) {
			if (reformat) {
				node = this.myLines.get(this.myStartLine).getNode();
				styleStack = replayStyleStack(node);
				this.myStartLine = 0;

				this.myLines.clear();
			} else {
				node = this.myLines.get(this.myLines.size() - 1).getNode();
				// System.out.println("node:" + node);
				styleStack = replayStyleStack(node);
				node = getParagraphNode(node, styleStack, true);
			}
		} else {
			node = this.myBook.getMainBody();
			styleStack = replayStyleStack(node);
			node = getParagraphNodeDown(node, styleStack, true);
		}

		renderPageFromNode(node, styleStack);
	}

	public void renderPageFromNode(INode node, IStyleStack styleStack) {
		int lineNum = this.myStartLine;

//		System.out.println("<" + lineNum);
		while (true) {
			if (lineNum >= this.myLines.size()) {
				if (node == null) {
					// don't scroll the text completely out
					// of screen
					if (this.myStartLine >= this.myLines.size()) {
						lineNum = this.myStartLine = this.myLines.size() - 1;
						continue;
					}

					this.myNextPageLine = this.myStartLine;
					return;
				}

				this.myLines.addAll(formatNode(node, styleStack, this.myPainter.getWidth() - styleStack.getMarginLeft() - styleStack.getMarginRight()));
				node = getParagraphNode(node, styleStack, true);
			}

			Line line = this.myLines.get(lineNum);

			if (this.myPainter.getYCoordinate() + line.getHeight() > this.myPainter.getHeight()) {
//				System.out.println(">" + lineNum);
				this.myNextPageLine = lineNum;
				cleanEndLines();
				return;
			}
			
			this.myPainter.addVerticalStrut(line.getHeight()-line.getDepth());
			this.myPainter.addHorizontalStrut(line.getLeftMargin());
			line.render();
			this.myPainter.addHorizontalStrut(-line.getWidth() - line.getLeftMargin());
			this.myPainter.addVerticalStrut(line.getDepth());

			lineNum ++;
		}

	}

	private IStyleStack replayStyleStack(INode node) {
		IStyleStack result = node.getBook().getSystemStyleSheet().newStyleStateStack();

		List<INode> backList = new ArrayList<INode>();

		while (node != null) {
			backList.add(node);
			node = node.getParentNode();
		}

		for (ListIterator<INode> iterator = backList.listIterator(backList.size()); iterator.hasPrevious();) {
			INode current = iterator.previous();
			result.pushTag(current.getTagName(), current.getNodeClass(), current.getID());
		}

		return result;
	}

	/**
	 * Removes unused cached lines from the start.
	 * 
	 */
	private void cleanStartLines() {
		int line = this.myStartLine;

		if (line == 0) {
			return;
		}

		ListIterator<Line> iterator = this.myLines.listIterator(line);
		INode node = this.myLines.get(this.myStartLine).getNode();

		while (iterator.hasPrevious()) {
			if (!node.equals(iterator.previous().getNode())) {
				iterator.next();
				break;
			}
		}

		while (iterator.hasPrevious()) {
			iterator.previous();
			this.myStartLine --;
			iterator.remove();
		}

	}

	private void cleanEndLines() {
		int line = this.myNextPageLine;

		if (line == -1) {
			return;
		}

		ListIterator<Line> iterator = this.myLines.listIterator(line);
		INode node = this.myLines.get(this.myNextPageLine).getNode();

		while (iterator.hasNext()) {
			if (!node.equals(iterator.next().getNode())) {
				iterator.previous();
				break;
			}
		}

		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}

	}

	/**
	 * Scrolls text one page up.
	 * 
	 */
	public void scrollPageUp() {
		double height = this.myPainter.getHeight();

		// FIXME: maybe remove lines after this.myStartLine ?
		// will be restored during repaint
		this.myNextPageLine = -1;

		if (this.myLines.isEmpty()) {
			return;
		}

		while (true) {
			if (this.myStartLine == 0) {
				if (formatPreviousNode()) {
					return;
				}
			}

			Line line = this.myLines.get(this.myStartLine);

			height = height - line.getHeight();

			if (height <= 0) {
				return;
			}

			this.myStartLine --;
		}
	}

	/**
	 * Scrolls text one page down.
	 * 
	 */
	public void scrollPageDown() {
		if (this.myNextPageLine < 0)
			return;
		this.myStartLine = this.myNextPageLine;

		cleanStartLines();
	}

	/**
	 * Scrolls text down by specified number of lines
	 * 
	 * @param lines the number of lines to scroll
	 */
	public void scrollDown(int lines) {
		this.myStartLine += lines;

		// will be restored during repaint
		this.myNextPageLine = -1;

		if (this.myStartLine >= this.myLines.size())
			return;

		cleanStartLines();

	}

	/**
	 * Scrolls text up by specified number of lines
	 * 
	 * @param lines the number of lines to scroll
	 */
	public void scrollUp(int lines) {
		this.myStartLine -= lines;
		// will be restored during repaint
		this.myNextPageLine = -1;

		if (this.myLines.isEmpty()) {
			return;
		}

		while (this.myStartLine < 0) {
			if (formatPreviousNode()) {
				this.myStartLine = 0;
				break;
			}
		}

	}

	/**
	 * Format previous paragraph node.
	 * 
	 * @return where the start of the book was reached.
	 */
	private boolean formatPreviousNode() {
		INode node = this.myLines.get(0).getNode();
		IStyleStack styleStack = replayStyleStack(node);
		node = getParagraphNode(node, styleStack, false);

		if (node == null) {
			return true;
		}

		List<Line> curParagraph = formatNode(node, styleStack, this.myPainter.getWidth() - styleStack.getMarginLeft() - styleStack.getMarginRight());

		this.myLines.addAll(0, curParagraph);
		this.myStartLine += curParagraph.size();

		return false;
	}

}
