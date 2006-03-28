package org.jbookreader.formatengine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jbookreader.book.bom.IBook;
import org.jbookreader.book.bom.IContainerNode;
import org.jbookreader.book.bom.INode;
import org.jbookreader.book.stylesheet.EDisplayType;
import org.jbookreader.book.stylesheet.IStyleSheet;
import org.jbookreader.formatengine.model.HorizontalGlue;
import org.jbookreader.formatengine.model.IRenderingObject;
import org.jbookreader.formatengine.model.Line;
import org.jbookreader.formatengine.model.MetaString;
import org.jbookreader.formatengine.model.RenderingDimensions;

/**
 * This class represents the core of the program: the text-formatting engine.
 * Each paragraph-level node is processed recursievly and each text node is
 * processed word-by-word. Words intermixed with horizontal glue objects are
 * put into lines. Then the list of lines is reprocessed and rendered with
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
	 * @param painter painter representing new output device.
	 */
	public void setPainter(IBookPainter painter) {
		this.myPainter = painter;
	}

	/**
	 * Sets the new book to be output.
	 * @param book new book to display.
	 */
	public void setBook(IBook book) {
		this.myBook = book;
		this.myStartLine = 0;
		this.myLines.clear();
	}
	
	/**
	 * Skip all whitespace in the provided string and return the the index of first non-whitespace char.
	 * @param text the string.
	 * @param start the start of the string
	 * @return the index of first non-whitespace character.
	 */
	private int consumeWhitespace(String text, int start) {
		while ((start < text.length()) && (text.charAt(start) <= '\u0020'))
			start ++;
		return start;
	}
	
	/**
	 * Formats a single paragraph node. Formatted lines are stored in the <code>result</code>
	 * list.
	 * TODO: style stack
	 * @param result the list with fully formatted lines
	 * @param currentLine the current line which maybe already has some rendered objects
	 * @param node the node to format
	 * @return new partially-formatted line.
	 */
	private Line formatNode(List<Line> result, Line currentLine, INode node) {
		if (node.isContainer()) {
			IContainerNode cnode = (IContainerNode) node;
			// TODO: apply styles
			for (INode childNode: cnode.getChildNodes()) {
				currentLine = formatNode(result, currentLine, childNode);
			}
			
			return currentLine;
		}

		String text = node.getText();

		if (text == null) {
			System.err.println("WARNING: node " + node.getTagName() + " doesn't has text");
			return currentLine;
		}

		int start = 0, end = 0;
		// TODO: font!
		ITextFont font = this.myPainter.getFont("default", 10);

		while (end < text.length()) {
			int temp = consumeWhitespace(text, start);
			if (temp > start) {
				// XXX: calculate more correct space size?
				double strut = font.getSpaceWidth();

				if (currentLine.getLeftMargin() + currentLine.getWidth() + strut + currentLine.getRightMargin()> this.myPainter.getWidth()) {
					currentLine = flushLine(result, currentLine);
				} else {
					currentLine.addObject(new HorizontalGlue(strut, this.myPainter));
				}
			}
			end = start = temp;

			if (end >= text.length())
				break;

			while ((end < text.length()) && (text.charAt(end) > '\u0020')) {
				end ++;
			}
			
			RenderingDimensions dim = this.myPainter.calculateStringDimensions(text, start, end, font);
			
			// XXX: this is the main place for rendering decision
			if (currentLine.getLeftMargin() + currentLine.getWidth() + dim.width + currentLine.getRightMargin() > this.myPainter.getWidth()
			    && !currentLine.getObjects().isEmpty()) {
				currentLine = flushLine(result, currentLine);
			}
			currentLine.addObject(new MetaString(text, start, end, this.myPainter, font));
			start = end;
		}

		return currentLine;
	}
	
	/**
	 * Flushes and pushes the line into resulting lines list.
	 * After that new line is creates and returned.
	 * @param result resulting lines list
	 * @param currentLine current line to flush
	 * @return new line for current paragraph.
	 */
	private Line flushLine(List<Line> result, Line currentLine) {
		// XXX: adjust glue objects in the line
		result.add(currentLine);
		INode node = currentLine.getParagraphNode();
		Line line = new Line(false, node);
		line.setLeftMargin(this.myBook.getSystemStyleSheet().getLeftMargin(node));
		line.setRightMargin(this.myBook.getSystemStyleSheet().getRightMargin(node));
		return line;
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
	 * The index of the line that shold start next page.
	 * Or <code>-1</code> if this line isn't calculated yet. 
	 */
	private int myNextPageLine = -1;
	
	/**
	 * Returns the paragraph node to be formatted right after <code>node</code>
	 * @param node current node
	 * @return next paragraph node.
	 */
	private INode getNextParagraphNode(INode node) {
		while (true) {
			IContainerNode pnode = node.getParentNode();

			// end of book
			// XXX: maybe handle other bodies?
			if (pnode == null) {
				return null;
			}

			List<INode> children = pnode.getChildNodes();
			int index = children.indexOf(node);
			if (index == -1) {
				throw new IllegalStateException("Node '" + node + "' not found in it's parent list!!!!");
			} else if (index +1 < children.size()) {
				node = children.get(index + 1);
				break;
			}
			node = pnode;
		}

		return getFirstParagraphNodeDown(node);
	}

	/**
	 * Returns the paragraph node to be formatted right before <code>node</code>
	 * @param node current node
	 * @return previous paragraph node.
	 */
	private INode getPreviousParagraphNode(INode node) {
		while (true) {
			IContainerNode pnode = node.getParentNode();

			// end of book
			// XXX: maybe handle other bodies?
			if (pnode == null) {
				return null;
			}

			List<INode> children = pnode.getChildNodes();
			int index = children.indexOf(node);
			if (index == -1) {
				throw new IllegalStateException("Node '" + node + "' not found in it's parent list!!!!");
			} else if (index - 1 >= 0) {
				node = children.get(index - 1);
				break;
			}
			node = pnode;
		}

		return getLastParagraphNodeDown(node);
	}
	
	/**
	 * Returns the first paragraph node in the specified container node.
	 * @param node the container node.
	 * @return the first paragraph node in the specified container node.
	 */
	private INode getFirstParagraphNodeDown(INode node) {
		IStyleSheet ssheet = node.getBook().getSystemStyleSheet();

		while (true) {
			if (!node.isContainer())
				return node;

			List<INode> children = ((IContainerNode) node).getChildNodes();
			if (children.isEmpty())
				return node;
			
			INode child = children.get(0);
			if  (ssheet.getNodeDisplayType(child) == EDisplayType.INLINE) {
				return node;
			}
			
			node = child;
		}
	}

	/**
	 * Returns the last paragraph node in the specified container node.
	 * @param node the container node.
	 * @return the last paragraph node in the specified container node.
	 */
	private INode getLastParagraphNodeDown(INode node) {
		IStyleSheet ssheet = node.getBook().getSystemStyleSheet();

		while (true) {
			if (!node.isContainer())
				return node;

			List<INode> children = ((IContainerNode) node).getChildNodes();
			if (children.isEmpty())
				return node;
			
			INode child = children.get(children.size()-1);
			if  (ssheet.getNodeDisplayType(child) == EDisplayType.INLINE) {
				return node;
			}
			
			node = child;
		}
	}

	// FIXME: move to stylesheet!
	// FIXME: make them font-size-dependant!
	/**
	 * The basic distance between lines
	 */
	private static final double BASE_LINE_SKIP = 12.0;
	/**
	 * The minimal distance between lines 
	 */
	private static final double LINE_SKIP_LIMIT = 1.0;
	/**
	 * The default distance between lines.
	 */
	private static final double LINE_SKIP = 1.0;
	
	/**
	 * Renders a page of text.
	 * @param reformat whether we could use cached preformatted lines from previous calls.
	 */
	public void renderPage(boolean reformat) {
		INode savedNode = null;
		
		this.myPainter.clear();

		if (reformat) {
			// FIXME: work with this case in more realistic maner
			if (this.myStartLine < this.myLines.size()) {
				savedNode = this.myLines.get(this.myStartLine).getParagraphNode();
				this.myStartLine = 0;
			}
			this.myLines.clear();
		}
		
		int lineNum = this.myStartLine;
		double previousDepth = 0;
		
		while (this.myPainter.getYCoordinate() < this.myPainter.getHeight()) {
			if (lineNum >= this.myLines.size()) {
//				System.out.println("here" + lineNum + " : " + this.myLines.size());
				INode node;
				if (this.myLines.size() == 0) {
					if (savedNode == null)
						node = getFirstParagraphNodeDown(this.myBook.getMainBody());
					else
						node = savedNode;
				} else {
					INode lastNode = this.myLines.get(this.myLines.size()-1).getParagraphNode();
					node = getNextParagraphNode(lastNode);
				}

				if (node == null) {
					// don't scroll the text completely out of screen
					if (this.myStartLine >= this.myLines.size()) {
						lineNum = this.myStartLine = this.myLines.size()-1;
						continue;
					}

					this.myNextPageLine = this.myStartLine;
					return;
				}
				
				Line cur = new Line(true, node);
				cur.setLeftMargin(this.myBook.getSystemStyleSheet().getFirstLineMargin(node));
				cur.setRightMargin(this.myBook.getSystemStyleSheet().getRightMargin(node));
				cur = formatNode(this.myLines, cur, node);
				this.myLines.add(cur);
			}
			
			Line line = this.myLines.get(lineNum);

			if (previousDepth + LINE_SKIP_LIMIT + line.getHeight() < BASE_LINE_SKIP) {
				this.myPainter.addVerticalStrut(BASE_LINE_SKIP);
			} else {
				this.myPainter.addVerticalStrut(previousDepth + LINE_SKIP + line.getHeight());
			}

			this.myPainter.addHorizontalStrut(line.getLeftMargin());
			for (Iterator<IRenderingObject> it = line.getObjects().iterator(); it.hasNext(); ) {
				IRenderingObject ro = it.next();
				if (ro.isGlue() && !it.hasNext())
					break;
				ro.render();
			}
			this.myPainter.flushString();
			
			previousDepth = line.getDepth();

			lineNum ++;
		}
		
		this.myNextPageLine = lineNum-1;
	}

	/**
	 * Removes unused cached lines from the start.
	 *
	 */
	private void cleanStartLines() {
		int line =  this.myStartLine;

		while (!this.myLines.get(line).isFirstLine())
			line --;
		
		for (int i = 0; i < line; i++)
			this.myLines.remove(0);

		this.myStartLine -= line;
		this.myNextPageLine -= line;
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
	 * @param lines the number of lines to scroll
	 */
	public void scrollDown(int lines) {
		this.myStartLine += lines;

		// will be restored during repaint
		this.myNextPageLine = -1;

		if (this.myStartLine >= this.myLines.size())
			return;
		
		if (this.myLines.get(this.myStartLine).isFirstLine())
			cleanStartLines();
		
	}

	/**
	 * Scrolls text up by specified number of lines
	 * @param lines the number of lines to scroll
	 */
	public void scrollUp(int lines) {
		this.myStartLine -= lines;
		// will be restored during repaint
		this.myNextPageLine = -1;
		
		while (this.myStartLine < 0) {
			// format previous paragraph
			INode node = getPreviousParagraphNode(this.myLines.get(0).getParagraphNode());
			
			if (node == null) {
				this.myStartLine = 0;
				return;
			}

			List<Line> curParagraph = new ArrayList<Line>();
			Line cur = new Line(true, node);
			cur.setLeftMargin(this.myBook.getSystemStyleSheet().getFirstLineMargin(node));
			cur.setRightMargin(this.myBook.getSystemStyleSheet().getRightMargin(node));
			cur = formatNode(curParagraph, cur, node);
			curParagraph.add(cur);
			
			this.myLines.addAll(0, curParagraph);
			this.myStartLine += curParagraph.size();
		}

	}

}
