package org.jbookreader.ui.swing.painter;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

import org.jbookreader.formatengine.ITextFont;

/**
 * This is the inner class used for representing fonts for the FE.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
class TextFontImpl implements ITextFont {
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
	public TextFontImpl(SwingBookPainter painter, String name, int size) {
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
}