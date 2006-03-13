package org.jbookreader.formatengine;

/**
 * This class represents dimensions of the string for precise positioning.
 * As usual, these dimensions don't specify 'the real dimensions', i.e. there can be pixels
 * higher than <code>StringDimesions.height</code> over the baseline, but you should
 * use the specified numbers for string positioning.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class StringDimensions {
	/**
	 * The height of the string over the baseline.
	 */
	public final double height;
	/**
	 * The height of the string below the baseline.
	 */
	public final double depth;
	/**
	 * The width of the string.
	 */
	public final double width;

	/**
	 * Constructs new dimensions object from specified numbers.
	 * @param height the height of the string over the baseline.
	 * @param depth the height of the string below the baseline.
	 * @param width the width of the string.
	 */
	public StringDimensions(double height, double depth, double width) {
		this.height = height;
		this.depth = depth;
		this.width = width;
	}
	
}
