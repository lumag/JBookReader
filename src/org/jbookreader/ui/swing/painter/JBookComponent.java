package org.jbookreader.ui.swing.painter;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import org.jbookreader.book.bom.IBook;
import org.jbookreader.formatengine.impl.FormatEngine;
import org.jbookreader.renderingengine.RenderingEngine;

/**
 * This class represents a book-viewing component.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class JBookComponent extends JComponent {
	/**
	 * Generated serial ID to make compilers happy.
	 */
	private static final long serialVersionUID = -2480969864889318912L;
	/**
	 * The corresponding painter.
	 */
	private final SwingBookPainter myPainter;
	/**
	 * The instance of the formatting engine that is used for formatting.
	 */
	private final RenderingEngine myEngine;
	/**
	 * The book that is displayed.
	 */
	private IBook myBook;
	private boolean myAntialias;
	private int myDefaultFontSize;
	
	/**
	 * This constructs new Book displaying component.
	 *
	 */
	public JBookComponent () {
		this.myEngine = new RenderingEngine(new FormatEngine());
		this.myPainter  = new SwingBookPainter();
		this.myEngine.setPainter(this.myPainter);
	}
	
	@Override
	public boolean isOpaque() {
		return true;
	}
	
	private int getPageHeight() {
		Insets insets = getInsets();
		return getHeight() - insets.top - insets.bottom;
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (isOpaque()) { // paint background
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
		}

		Insets insets = getInsets();
		int w = getWidth() - insets.left - insets.right;
		int h = getPageHeight();
//		System.out.println(w + "x" + h);

		Graphics2D g2d = (Graphics2D) g.create(insets.left, insets.top, w, h); // copy g
		
		g2d.setBackground(getBackground());
		
		if (this.myPainter.getWidth() != w) {
			this.myEngine.flush();
		}

		if (this.myAntialias) {
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		}

		this.myPainter.setGraphics(g2d, w, h);
		
		if (this.myBook != null) {
			// FIXME: move to separate thread, add buffer, etc.!
			this.myEngine.renderPage();
		}

		g2d.dispose();

	}

	/**
	 * Sets the book to be displayed and redisplays the view. 
	 * @param book new book to display
	 */
	public void setBook(IBook book) {
		this.myBook = book;
		this.myEngine.setBook(this.myBook);
		repaint();
	}

	// FIXME: don't overscroll, or we miss broken line!
	/**
	 * Scrolls the display one page down
	 *
	 */
	public void scrollPageDown() {
		this.myEngine.scroll(this.getPageHeight() - this.myDefaultFontSize * 2);
		repaint();
	}

	/**
	 * Scrolls the display one page up
	 *
	 */
	public void scrollPageUp() {
		this.myEngine.scroll( - (this.getPageHeight() - this.myDefaultFontSize * 2));
		repaint();
	}

	/**
	 * Scroll the display down by specified amount of pixels
	 * @param pixels the amount of pixels to scroll
	 */
	public void scrollDown(int pixels) {
		this.myEngine.scroll(pixels);
		repaint();
	}

	/**
	 * Scroll the display up by specified amount of pixels
	 * @param pixels the amount of pixels to scroll
	 */
	public void scrollUp(int pixels) {
		this.myEngine.scroll( - pixels);
		repaint();
	}
	
	/**
	 * Sets default font to be used for rendering.
	 * @param family the family of the font
	 * @param size font size
	 */
	public void setDefaultFont(String family, int size) {
		this.myDefaultFontSize = size;
		this.myEngine.setDefaultFont(family, size);
		this.myEngine.flush();
		repaint();
	}

	/**
	 * Whether to antialias rendered parts.
	 * @return whether to antialias rendered parts.
	 */
	public boolean isAntialias() {
		return this.myAntialias;
	}

	/**
	 * Sets whether to antialias rendered parts.
	 * @param antialias whether to antialias rendered parts.
	 */
	public void setAntialias(boolean antialias) {
		this.myAntialias = antialias;
	}

	public String getDisplayNodeReference() {
		return this.myEngine.getDisplayNodeReference();
	}

	public void setBookPositionByReference(String reference) {
		this.myEngine.scrollToReference(reference);
	}
}
