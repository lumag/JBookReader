package org.jbookreader.util;

import java.io.InputStream;
import java.io.PrintWriter;

import org.jbookreader.book.bom.INode;
import org.jbookreader.formatengine.IBookPainter;
import org.jbookreader.formatengine.IRenderingObject;
import org.jbookreader.formatengine.IFont;
import org.jbookreader.formatengine.RenderingDimensions;

/**
 * This class represents a simple painter over the console with width 80.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class TextPainter implements IBookPainter {
	
	/**
	 * Output device.
	 */
	private final PrintWriter myOutput;
	
	/**
	 * Current X coordinate.
	 */
	private int myX;

	/**
	 * The width of formatted text.
	 */
	private double myWidth;

	/**
	 * This constructs new <code>TextPainter</code> with specified
	 * <code>PrintWriter</code> as the output device.
	 * @param output output stream
	 * @param width the width of text stram
	 */
	public TextPainter(PrintWriter output, int width) {
		this.myOutput = output;
		this.myWidth = width;
	}

	public void clear() {
		this.myX = 0;
	}

	public double getWidth() {
		return this.myWidth;
	}

	public double getHeight() {
		return Double.POSITIVE_INFINITY;
	}
	
	public void addHorizontalStrut(double size) {
		while (size >= 0.5) {
			this.myOutput.append(' ');
			this.myX ++;
			size -= 1;
		}
	}

	public void addVerticalStrut(double size) {
		// ignored
	}

	public void flushLine() {
		this.myX = 0;
		this.myOutput.println();
	}

	/**
	 * This closes the writer.
	 */
	public void close() {
		this.myOutput.close();
	}

	/**
	 * The w/a for the all font problems.
	 */
	private IFont myFont; 
	public IFont getFont(String name, int size) {
		if (this.myFont == null) {
			this.myFont = new IFont() {

				public double getSpaceWidth() {
					return 1;
				}

				public RenderingDimensions calculateStringDimensions(String s, int start, int end) {
					return new RenderingDimensions(1, 0,  end - start);
				}

				@SuppressWarnings("synthetic-access")
				public void renderString(String s, int start, int end) {
					TextPainter.this.myOutput.append(s, start, end);
				}

			};
		}
		return this.myFont;
	}

	public double getXCoordinate() {
		return this.myX;
	}
	public double getYCoordinate() {
		// allways 0, as we don't count vertical size
		return 0;
	}

	public IRenderingObject getImage(INode node, String contentType, InputStream stream) {
		// allways null: we can't render images.
		return null;
	}

}
