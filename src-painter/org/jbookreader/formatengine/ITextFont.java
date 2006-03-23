package org.jbookreader.formatengine;

/**
 * This interface represents a font used for outputting on the {@link org.jbookreader.formatengine.IBookPainter}
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public interface ITextFont {
	/**
	 * Returns the width of space in this font.
	 * @return the width of space in this font.
	 */
	double getSpaceWidth();
}
