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
	private IBookPainter myPainter;
	private IBook myBook;

	public void setPainter(IBookPainter painter) {
		this.myPainter = painter;
	}

	public void setBook(IBook book) {
		this.myBook = book;
		this.myStartLine = 0;
		this.myLines.clear();
	}
	
	private int consumeWhitespace(String text, int start) {
		while ((start < text.length()) && (text.charAt(start) <= '\u0020'))
			start ++;
		return start;
	}
	
	// TODO: style stack
	private Line formatNode(List<Line> result, Line currentLine, IContainerNode cnode) {
		for (INode node: cnode.getChildNodes()) {
			if (node.isContainer()) {
				// TODO: apply styles
				currentLine = formatNode(result, currentLine, (IContainerNode) node);
			} else {
				String text = node.getText();
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
			}
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
		IContainerNode node = currentLine.getParagraphNode();
		Line line = new Line(false, node);
		line.setLeftMargin(this.myBook.getSystemStyleSheet().getLeftMargin(node));
		line.setRightMargin(this.myBook.getSystemStyleSheet().getRightMargin(node));
		return line;
	}

	private List<Line> myLines = new ArrayList<Line>();
	private int myStartLine;
	private int myNextPageLine;
	
	private IContainerNode getNextParagraphNode(IContainerNode node) {
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
				node = (IContainerNode) children.get(index + 1);
				break;
			}
			node = pnode;
		}

		return getFirstParagraphNodeDown(node);
	}

	private IContainerNode getFirstParagraphNodeDown(IContainerNode node) {
		IStyleSheet ssheet = node.getBook().getSystemStyleSheet();

		while (true) {
			if (!node.isContainer())
				return node;

			List<INode> children = node.getChildNodes();
			if (children.isEmpty())
				return node;
			
			INode child0 = children.get(0);
			if  (ssheet.getNodeDisplayType(child0) == EDisplayType.INLINE) {
				return node;
			}
			
			if (!child0.isContainer())
				throw new IllegalStateException("child node isn't INLINE, but isn't a container: " + node.getTagName() + " -> " + child0.getTagName());

			node = (IContainerNode) child0;
		}
	}

	// FIXME: move to stylesheet!
	// FIXME: make them font-size-dependant!
	private static final double BASE_LINE_SKIP = 12.0;
	private static final double LINE_SKIP_LIMIT = 1.0;
	private static final double LINE_SKIP = 1.0;
	
	public void renderPage(boolean reformat) {
		IContainerNode savedNode = null;
		
		this.myPainter.clear();

		if (reformat) {
			// FIXME: work with this case in more realistic maner
			if (this.myStartLine < this.myLines.size()) {
				savedNode = this.myLines.get(this.myStartLine).getParagraphNode();
				// int line =  this.myStartLine;

				// while (!this.myLines.get(line).isFirstLine())
				//	line --;

				// this.myStartLine -= line; 
				this.myStartLine = 0;
			}
			this.myLines.clear();
		}
		
		int lineNum = this.myStartLine;
		double previousDepth = 0;
		
		while (this.myPainter.getYCoordinate() < this.myPainter.getHeight()) {
			if (lineNum >= this.myLines.size()) {
//				System.out.println("here" + lineNum + " : " + this.myLines.size());
				IContainerNode node;
				if (this.myLines.size() == 0) {
					if (savedNode == null)
						node = getFirstParagraphNodeDown(this.myBook.getMainBody());
					else
						node = savedNode;
				} else {
					IContainerNode lastNode = this.myLines.get(this.myLines.size()-1).getParagraphNode();
					node = getNextParagraphNode(lastNode);
				}

				if (node == null) {
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

	public void scrollPageDown() {
		this.myStartLine = this.myNextPageLine;
	}
}
