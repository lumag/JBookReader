package org.jbookreader.formatengine;

/**
 * This interface represents a font used for outputting on the {@link org.jbookreader.formatengine.IBookPainter}
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public interface IFont {
	/**
	 * Returns the width of space in this font.
	 * @return the width of space in this font.
	 */
	double getSpaceWidth();
	/**
	 * Calculates dimensions to be used for rendering the specified string part
	 * with given font.
	 * @param s the string to calculate dimensions for 
	 * @param start The index of the first character in the string
	 * @param end The index of the character following the last character in the subsequence
	 * @return the dimensions of the string when rendered.
	 */
	RenderingDimensions calculateStringDimensions(String s, int start, int end);
	/**
	 * Renders the string with given font
	 * @param s the string to render
	 * @param start The index of the first character in the string
	 * @param end The index of the character following the last character in the subsequence
	 * @param dimensions The dimensions from calculateStringDimensions()
	 */
	void renderString(String s, int start, int end, RenderingDimensions dimensions);
	
}
