package org.jbookreader.formatengine;

import org.jbookreader.book.bom.INode;

/**
 * This interface represents an abstract rendering object.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 * 
 */
public interface IRenderingObject {

	double getHeight();

	double getDepth();

	double getWidth();

	/**
	 * Renders the object to assigned
	 * {@link org.jbookreader.formatengine.IBookPainter}.
	 * 
	 */
	void render();

	INode getNode();
}