package org.jbookreader.formatengine;

import java.io.InputStream;

import org.jbookreader.book.bom.INode;

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
	 * 
	 * @return the width of the device.
	 */
	double getWidth();

	/**
	 * Returns the height of the device.
	 * 
	 * @return the height of the device.
	 */
	double getHeight();

	/**
	 * This adds the horizontal space of given size.
	 * 
	 * @param size the size of necessary horizontal space.
	 */
	void addHorizontalStrut(double size);

	/**
	 * This adds the vertical space of given size.
	 * 
	 * @param size the size of necessary vertical space.
	 */
	void addVerticalStrut(double size);

	/**
	 * Moves the cursor to the start of the string..
	 */
	void flushLine();

	/**
	 * Allocates and returns specified font.
	 * 
	 * @param name the name of the font
	 * @param size font size
	 * @return the allocated font object.
	 */
	IFont getFont(String name, int size);

	/**
	 * Returns current X coordinate.
	 * 
	 * @return current X coordinate.
	 */
	double getXCoordinate();

	/**
	 * Returns current Y coordinate.
	 * 
	 * @return current Y coordinate.
	 */
	double getYCoordinate();

	/**
	 * Returns a rendering object for the image or <code>null</code> if
	 * the image can't be rendered.
	 * @param node TODO
	 * @param contentType the content type of the image
	 * @param stream the stream with the image
	 * 
	 * @return a rendering object for the image.
	 */
	IRenderingObject getImage(INode node, String contentType, InputStream stream);
}
