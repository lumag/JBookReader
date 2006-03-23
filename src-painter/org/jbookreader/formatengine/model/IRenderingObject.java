package org.jbookreader.formatengine.model;

/**
 * This interface represents an abstract rendering object.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public interface IRenderingObject {
	
	/**
	 * Returns the dimensions of this object. Be warned, that these are logical dimensions,
	 * not physical ones, i.e. the content of the object can get outside of the specified box.
	 * 
	 * In other words: they are rendering dimensions, not bounding box
	 * @return the dimensions of this object.
	 */
	 RenderingDimensions getDimensions();

	 /**
	  * Returns true if the object is a glue (i.e. it's whitespace).
	  * @return true if the object is a glue.
	  */
	boolean isGlue();
	/**
	 * Renders the object to assigned {@link org.jbookreader.formatengine.IBookPainter}.
	 *
	 */
	 void render();
}