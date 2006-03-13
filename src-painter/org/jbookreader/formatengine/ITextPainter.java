package org.jbookreader.formatengine;

/**
 * This interface represents a not-so-simple displaying device.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public interface ITextPainter {
	/**
	 * Clears the output device, if it makes sense.
	 *
	 */
	void clear();

	/**
	 * Returns the width of the device.
	 * @return the width of the device.
	 */
	double getWidth();
	/**
	 * Returns the height of the device.
	 * @return the height of the device.
	 */
	double getHeight();
	
	double getXPosition();
	double getYPosition();
	
	/**
	 * Calculates dimensions to be used for rendering the specified string
	 * with given font.
	 * @param s the string to calculate dimensions for 
	 * @param start TODO
	 * @param end TODO
	 * @param font the font to work with
	 * @return the dimensions of the string when rendered.
	 */
	StringDimensions calculateStringDimensions(String s, int start, int end, ITextFont font);
	/**
	 * Renders the string with given font
	 * @param s the string to render
	 * @param start TODO
	 * @param end TODO
	 * @param font the font to use for rendering
	 */
	void renderString(String s, int start, int end, ITextFont font);
	
	/**
	 * This adds the horizontal space of given size.
	 * @param size the size of necessary horizontal space.
	 */
	void addHorizontalStrut(double size);
	
	/**
	 * This starts new string output after adding given space between string baselines
	 *  FIXME: think some less idiotic for this!
	 * @param vStrut
	 */
	void flushString(double vStrut);
	
	ITextFont getFont(String name, int size);
}
