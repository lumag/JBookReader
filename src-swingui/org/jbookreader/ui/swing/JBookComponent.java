package org.jbookreader.ui.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.JComponent;

import org.jbookreader.book.bom.IBook;
import org.jbookreader.formatengine.FormatEngine;
import org.jbookreader.ui.swing.painter.SwingBookPainter;

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
	private final FormatEngine myEngine;
	/**
	 * The book that is displayed.
	 */
	private IBook myBook;
	/**
	 * Wether the diplayed part of the book should be reformatted. 
	 */
	private boolean myReformatBook;
	
	/**
	 * This constructs new Book displaying component.
	 *
	 */
	public JBookComponent () {
		this.myEngine = new FormatEngine();
		this.myPainter  = new SwingBookPainter();
		this.myEngine.setPainter(this.myPainter);
	}
	
	@Override
	public boolean isOpaque() {
		return true;
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (isOpaque()) { // paint background
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
		}

		Insets insets = getInsets();
		int w = getWidth() - insets.left - insets.right;
		int h = getHeight() - insets.top - insets.bottom;
//		System.out.println(w + "x" + h);

		Graphics2D g2d = (Graphics2D) g.create(insets.left, insets.top, w, h); // copy g
		
		g2d.setBackground(getBackground());
		
		if (this.myPainter.getWidth() != w)
			this.myReformatBook = true;

		this.myPainter.setGraphics(g2d, w, h);
		
		if (this.myBook != null) {
			// FIXME: move to separate thread, add buffer, etc.!
			this.myEngine.renderPage(this.myReformatBook);
			this.myReformatBook = false;
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
		this.myReformatBook = true;
		repaint();
	}

	/**
	 * Scrolls the display one page down
	 *
	 */
	public void scrollPageDown() {
		this.myEngine.scrollPageDown();
		repaint();
	}

	/**
	 * Scrolls the display one page up
	 *
	 */
	public void scrollPageUp() {
		this.myEngine.scrollPageUp();
		repaint();
	}

	/**
	 * Scroll the display down by specified amount of lines
	 * @param lines the amount of lines to scroll
	 */
	public void scrollDown(int lines) {
		this.myEngine.scrollDown(lines);
		repaint();
	}

	/**
	 * Scroll the display up by specified amount of lines
	 * @param lines the amount of lines to scroll
	 */
	public void scrollUp(int lines) {
		this.myEngine.scrollUp(lines);
		repaint();
	}

}
