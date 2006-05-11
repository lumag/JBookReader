package org.jbookreader.renderingengine;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.WeakHashMap;

import org.jbookreader.book.bom.IBook;
import org.jbookreader.book.bom.IContainerNode;
import org.jbookreader.book.bom.INode;
import org.jbookreader.book.stylesheet.EDisplayType;
import org.jbookreader.book.stylesheet.IStyleStack;
import org.jbookreader.formatengine.IBookPainter;
import org.jbookreader.formatengine.IFormatEngine;
import org.jbookreader.formatengine.IRenderingObject;

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
public class RenderingEngine {
	/**
	 * Current book painter.
	 */
	private IBookPainter myPainter;

	/**
	 * Current book.
	 */
	private IBook myBook;
	
	private final IFormatEngine myFormatEngine;

	private String myFontFamily;

	private int myFontSize;
	
	private INode myStartNode;
	private double myStartY;
	private double myPageHeight;
	
	private Map<INode, List<IRenderingObject>> myFormattedNodes = new WeakHashMap<INode, List<IRenderingObject>>();
	
	/**
	 * This constructs new Rendering engine for the given format engine.
	 * @param formatEngine the format engine to use for formatting
	 */
	public RenderingEngine(IFormatEngine formatEngine) {
		this.myFormatEngine = formatEngine;
	}


	/**
	 * Sets <code>painter</code> as a new output device.
	 * 
	 * @param painter painter representing new output device.
	 */
	public void setPainter(IBookPainter painter) {
		this.myPainter = painter;
		this.myPageHeight = painter.getHeight();
	}
	

	/**
	 * Sets the new book to be output.
	 * 
	 * @param book new book to display.
	 */
	public void setBook(IBook book) {
		this.myBook = book;
		INode node = this.myBook.getMainBody();
		IStyleStack styleStack = replayStyleStack(node);
		this.myStartNode = getParagraphNodeDown(node, styleStack, true);

		flush();
	}

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
			if (children.isEmpty()) {
				return node;
			}

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
	
//	private int access;
//	private int misses;
//	
	private List<IRenderingObject> getFormattedNode(INode node, IStyleStack styleStack, double width) {
		List<IRenderingObject> robject = this.myFormattedNodes.get(node);
//		this.access ++;
		if ((robject == null)/* || (robject.getWidth() != width)*/) {
			robject = this.myFormatEngine.formatParagraphNode(this.myPainter, node, styleStack, width);
			this.myFormattedNodes.put(node, robject);
//			this.misses ++;
		}
		
		return robject;
	}

	/**
	 * Renders a page of text.
	 */
	public void renderPage() {
		INode node = null;
		IStyleStack styleStack;

		this.myPainter.clear();

		this.myPageHeight = this.myPainter.getHeight();

		node = this.myStartNode;
		if (node == null) {
			return;
		}
		
		styleStack = replayStyleStack(node);

		// FIXME: position handling (after I implement more correct node references)
//		if (this.myStartY <= 0) {
//			while (true) {
//				List<IRenderingObject> paragraph = getFormattedNode(node, styleStack, this.myPainter.getWidth());
//				if (this.myStartY + paragraph.getHeight() >= 0) {
//					break;
//				}
//				node = getParagraphNode(node, styleStack, true);
//				if (node == null) {
//					node = this.myStartNode;
//					styleStack = replayStyleStack(node);
//					this.myStartY = 0;
//					break;
//				}
//				this.myStartY += paragraph.getHeight();
//				this.myStartNode = node;
//			}
//		} else {
//			while (true) {
//				node = getParagraphNode(node, styleStack, false);
//				if (node == null) {
//					node = this.myStartNode;
//					styleStack = replayStyleStack(node);
//					this.myStartY = 0;
//					break;
//				}
//				List<IRenderingObject> paragraph = getFormattedNode(node, styleStack, this.myPainter.getWidth());
//				this.myStartY -= paragraph.getHeight();
//				this.myStartNode = node;
//				if (this.myStartY <= 0) {
//					break;
//				}
//			}
//		}

		this.myPainter.addVerticalStrut(this.myStartY);
		
//		System.out.println(node.getNodeReference() + " --- " + this.myBook.getNodeByReference(node.getNodeReference()).getNodeReference());
		
		while (node != null) {
			double currentY = this.myPainter.getYCoordinate();
			if (currentY >= this.myPageHeight) {
//				System.out.println((1.0*this.misses)/this.access);
				return;
			}

			List<IRenderingObject> paragraph = getFormattedNode(node, styleStack, this.myPainter.getWidth());

			for (IRenderingObject robject: paragraph) {
				robject.render();
			}
			
			node = getParagraphNode(node, styleStack, true);
		}

	}

	private IStyleStack replayStyleStack(INode node) {
		IStyleStack result = node.getBook().getSystemStyleSheet().newStyleStateStack();
		if (this.myFontFamily != null) {
			result.setDefaultFontFamily(this.myFontFamily);
		}
		if (this.myFontSize != 0) {
			result.setDefaultFontSize(this.myFontSize);
		}

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
	 * Scrolls text down by specified number of pixels
	 * 
	 * @param pixels the amount of lines to scroll
	 */
	public void scroll(int pixels) {
		this.myStartY -= pixels;
	}

	/**
	 * Sets default font to be used for rendering.
	 * @param family the family of the font
	 * @param size font size
	 */
	public void setDefaultFont(String family, int size) {
		this.myFontFamily = family;
		this.myFontSize = size;
	}

	/**
	 * Flush all internal caches, etc.
	 */
	public void flush() {
		this.myFormattedNodes.clear();
		this.myStartY = 0;
	}

	public String getDisplayNodeReference() {
		if (this.myStartNode == null) {
			return null;
		}

		return this.myStartNode.getNodeReference();
	}


	public void scrollToReference(String reference) {
		INode node = this.myBook.getNodeByReference(reference);
		if (node == null) {
			System.err.println("Bad book reference passed: " + reference);
			return;
		}
		this.myStartNode = node;
		flush();
	}
}
