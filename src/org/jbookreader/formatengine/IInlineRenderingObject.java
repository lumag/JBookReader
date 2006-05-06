package org.jbookreader.formatengine;

public interface IInlineRenderingObject extends IRenderingObject {
	/**
	 * Returns the depth (the distance between the baseline
	 * and the bottom) of the object.
	 * @return the depth of the object.
	 */
	double getDepth();

	void renderInline();
	
}
