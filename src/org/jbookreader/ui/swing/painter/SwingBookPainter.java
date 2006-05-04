package org.jbookreader.ui.swing.painter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.InputStream;

import org.jbookreader.book.bom.INode;
import org.jbookreader.formatengine.IBookPainter;
import org.jbookreader.formatengine.IRenderingObject;
import org.jbookreader.formatengine.IFont;

/**
 * This is swing-based implementation of the {@link org.jbookreader.formatengine.IBookPainter}.
 * Currently it calls all {@link org.jbookreader.formatengine.impl.FormatEngine} from Swing tread.
 * @TODO move calling to other tread, make engine paint over buffering image, etc.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class SwingBookPainter implements IBookPainter {
	/**
	 * The width of the corresponding component.
	 */
	private double myWidth;
	/**
	 * The height of the corresponding component.
	 */
	private double myHeight;
	/**
	 * Current graphics object
	 */
	private Graphics2D myGraphics;
	
	/**
	 * Current X.
	 */
	private float myCurrentX;
	/**
	 * Current Y.
	 */
	private float myCurrentY;
	
	/**
	 * Sets the Graphics2D object for rendering
	 * @param g2d the G2D object
	 * @param width new width
	 * @param height new height
	 */
	public void setGraphics(Graphics2D g2d, double width, double height) {
		this.myGraphics = g2d;
		this.myWidth = width;
		this.myHeight = height;
		this.myCurrentX = this.myCurrentY = 0;
	}

	public void clear() {
		this.myGraphics.setColor(this.myGraphics.getBackground());
		this.myGraphics.fill(this.myGraphics.getClip());

		this.myGraphics.setColor(Color.BLACK);
		this.myGraphics.drawRect(0, 0, (int)this.myWidth-1, (int)this.myHeight-1);

		this.myCurrentX = this.myCurrentY = 0;
	}

	public double getHeight() {
		return this.myHeight;
	}

	public double getWidth() {
		return this.myWidth;
	}

	public void addHorizontalStrut(double size) {
		this.myCurrentX += size;
	}

	public void addVerticalStrut(double size) {
		this.myCurrentY += size;
	}

	public IFont getFont(String name, int size) {
		return new SwingFont(this, name, size);
	}

	public double getXCoordinate() {
		return this.myCurrentX;
	}

	public double getYCoordinate() {
		return this.myCurrentY;
	}

	public IRenderingObject getImage(INode node, String contentType, InputStream stream) {
		try {
			return new ImageRenderingObject(this, node, stream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns the graphics rendering context.
	 * @return the graphics rendering context.
	 */
	Graphics2D getGraphics() {
		return this.myGraphics;
	}

}