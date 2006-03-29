package org.jbookreader.ui.swing.painter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;

import org.jbookreader.formatengine.IFont;
import org.jbookreader.formatengine.RenderingDimensions;

/**
 * This is internal class used for representing Swing fonts for the FE.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
class SwingFont implements IFont {
	/**
	 * 
	 */
	private final SwingBookPainter myPainter;
	/**
	 * Font object from AWT
	 */
	final Font myFont;
	/**
	 * The width of single space in this font.
	 */
	double mySpaceWidth = 0; 
	
	/**
	 * This constructs the class representing the font with given name and size
	 * @param name the name of the font
	 * @param size the size of the font
	 * @param painter TODO
	 */
	public SwingFont(SwingBookPainter painter, String name, int size) {
		this.myPainter = painter;
		this.myFont = new Font(name, Font.PLAIN, size);
	}

	public double getSpaceWidth() {
		if (this.mySpaceWidth == 0) {
			FontRenderContext frc = this.myPainter.getGraphics().getFontRenderContext();
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

	public RenderingDimensions calculateStringDimensions(String s, int start, int end) {
		Font font = this.getFont();
		FontRenderContext frc = this.myPainter.getGraphics().getFontRenderContext();
		LineMetrics metrics = font.getLineMetrics(s, start, end, frc);
		Rectangle2D r2d = font.getStringBounds(s, start, end, frc);
		return new RenderingDimensions(metrics.getAscent(), metrics.getDescent(), r2d.getMaxX() - r2d.getMinX());
	}

	public void renderString(String s, int start, int end, RenderingDimensions dimensions) {
		Font font = this.getFont();
		Graphics2D graphics = this.myPainter.getGraphics();
		if (!graphics.getFont().equals(font))
			graphics.setFont(font);
		graphics.setColor(Color.BLACK);
		graphics.drawString(s.substring(start, end), (float)this.myPainter.getXCoordinate(), (float)this.myPainter.getYCoordinate());
		this.myPainter.addHorizontalStrut(dimensions.width);
	}

}