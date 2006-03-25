package org.jbookreader.ui.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;

import org.jbookreader.formatengine.ITextFont;
import org.jbookreader.formatengine.IBookPainter;
import org.jbookreader.formatengine.model.RenderingDimensions;

/**
 * This is swing-based implementation of the {@link org.jbookreader.formatengine.IBookPainter}.
 * Currently it calls all {@link org.jbookreader.formatengine.FormatEngine} from Swing tread.
 * @todo TODO move calling to other tread, make engine paint over buffering image, etc.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class SwingBookPainter implements IBookPainter {
	/**
	 * This is the inner class used for representing fonts for the FE.
	 * 
	 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
	 *
	 */
	private class TextFontImpl implements ITextFont {
		/**
		 * Font object from AWT
		 */
		private final Font myFont;
		/**
		 * The width of single space in this font.
		 */
		private double mySpaceWidth = 0; 
		
		/**
		 * This constructs the class representing the font with given name and size
		 * @param name the name of the font
		 * @param size the size of the font
		 */
		public TextFontImpl(String name, int size) {
			this.myFont = new Font(name, Font.PLAIN, size);
		}

		public double getSpaceWidth() {
			if (this.mySpaceWidth == 0) {
				FontRenderContext frc = SwingBookPainter.this.myGraphics.getFontRenderContext();
				GlyphVector gv = this.myFont.createGlyphVector(frc, new char[]{' '});
				Rectangle2D r2d = gv.getLogicalBounds();
				this.mySpaceWidth = r2d.getMaxX() - r2d.getMinX();
			}
			
			return this.mySpaceWidth;
		}

		/**
		 * Returns the corresponding Swing Font object.
		 * @return the corresponding Swing Font object.
		 */
		public Font getFont() {
			return this.myFont;
		}
	}

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

	public RenderingDimensions calculateStringDimensions(String s, int start, int end, ITextFont ifont) {
		TextFontImpl fontImpl = (TextFontImpl)ifont;
		Font font = fontImpl.getFont();
		FontRenderContext frc = this.myGraphics.getFontRenderContext();
		LineMetrics metrics = font.getLineMetrics(s, start, end, frc);
		Rectangle2D r2d = font.getStringBounds(s, start, end, frc);
		return new RenderingDimensions(metrics.getAscent(), metrics.getDescent(), r2d.getMaxX() - r2d.getMinX());
	}

	public void renderString(String s, int start, int end, ITextFont ifont, RenderingDimensions dimensions) {
		TextFontImpl fontImpl = (TextFontImpl)ifont;
		Font font = fontImpl.getFont();
		if (!this.myGraphics.getFont().equals(font))
			this.myGraphics.setFont(font);
		this.myGraphics.setColor(Color.BLACK);
		this.myGraphics.drawString(s.substring(start, end), this.myCurrentX, this.myCurrentY);
		this.myCurrentX += dimensions.width;
	}

	public void addHorizontalStrut(double size) {
		this.myCurrentX += size;
	}

	public void addVerticalStrut(double size) {
		this.myCurrentY += size;
	}

	public void flushString() {
		this.myCurrentX = 0;
	}

	public ITextFont getFont(String name, int size) {
		return new TextFontImpl(name, size);
	}

	public double getYCoordinate() {
		return this.myCurrentY;
	}

}