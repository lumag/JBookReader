package org.jbookreader.formatengine;

import org.jbookreader.book.bom.INode;

/**
 * This interface represents a rendering object.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 * 
 */
public interface IRenderingObject {

	/**
	 * Returns the total height of the object.
	 * @return the total height of the object.
	 */
	double getHeight();

	/**
	 * Returns the width of the object.
	 * @return the width of the object.
	 */
	double getWidth();

	/**
	 * Renders the object to assigned
	 * {@link org.jbookreader.formatengine.IBookPainter}.
	 * 
	 */
	void render();

	/**
	 * Returns the node which this rendering object represents.
	 * @return the node which this rendering object represents.
	 */
	INode getNode();
	
	void setMargins(double top, double right, double bottom, double left);
}