package org.jbookreader.formatengine;

import java.io.InputStream;

import org.jbookreader.formatengine.model.IRenderingObject;
import org.jbookreader.formatengine.model.RenderingDimensions;

/**
 * This interface represents a not-so-simple displaying device.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public interface IBookPainter {
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
	
	/**
	 * Calculates dimensions to be used for rendering the specified string part
	 * with given font.
	 * @param s the string to calculate dimensions for 
	 * @param start The index of the first character in the string
	 * @param end The index of the character following the last character in the subsequence
	 * @param font the font to work with
	 * @return the dimensions of the string when rendered.
	 */
	RenderingDimensions calculateStringDimensions(String s, int start, int end, ITextFont font);
	/**
	 * Renders the string with given font
	 * @param s the string to render
	 * @param start The index of the first character in the string
	 * @param end The index of the character following the last character in the subsequence
	 * @param font the font to use for rendering
	 * @param dimensions The dimensions from calculateStringDimensions()
	 */
	void renderString(String s, int start, int end, ITextFont font, RenderingDimensions dimensions);
	
	/**
	 * This adds the horizontal space of given size.
	 * @param size the size of necessary horizontal space.
	 */
	void addHorizontalStrut(double size);
	
	/**
	 * This adds the vertical space of given size.
	 * @param size the size of necessary vertical space.
	 */
	void addVerticalStrut(double size);
	
	/**
	 * Moves the cursor to the start of the string..
	 */
	void flushString();
	
	/**
	 * Allocates and returns specified font.
	 * @param name the name of the font
	 * @param size font size
	 * @return the allocated font object.
	 */
	ITextFont getFont(String name, int size);

	/**
	 * Returns current X coordinate.
	 * @return current X coordinate. 
	 */
	double getXCoordinate();

	/**
	 * Returns current Y coordinate.
	 * @return current Y coordinate. 
	 */
	double getYCoordinate();
	
	/**
	 * Returns a rendering object for the image or <code>null</code> if the image
	 * can't be rendered. 
	 * @param contentType the content type of the image
	 * @param stream the stream with the image
	 * @return a rendering object for the image.
	 */
	IRenderingObject getImage(String contentType, InputStream stream);
}
