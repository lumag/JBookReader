package org.jbookreader.formatengine;

/**
 * This class represents dimensions of the rendering object for precise positioning.
 * As usual, these dimensions don't specify 'the real dimensions', i.e. there can be pixels
 * higher than <code>StringDimesions.height</code> over the baseline, but you should
 * use the specified numbers for string positioning.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class RenderingDimensions {
	/**
	 * The height of the string over the baseline.
	 */
	public final double ascent;
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
	 * @param ascent the height of the string over the baseline.
	 * @param depth the height of the string below the baseline.
	 * @param width the width of the string.
	 */
	public RenderingDimensions(double ascent, double depth, double width) {
		this.ascent = ascent;
		this.depth = depth;
		this.width = width;
	}
	
}
