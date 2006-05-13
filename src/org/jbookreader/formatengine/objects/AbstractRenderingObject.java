package org.jbookreader.formatengine.objects;

import org.jbookreader.book.bom.INode;
import org.jbookreader.formatengine.IBookPainter;
import org.jbookreader.formatengine.IRenderingObject;

/**
 * This class represents an abstract rendering object.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public abstract class AbstractRenderingObject implements IRenderingObject {
	interface BoxSides {
		int TOP = 0;
		int RIGHT = 1;
		int BOTTOM = 2;
		int LEFT = 3;
	}
	/**
	 * The height of the rendering object above the baseline.
	 */
	private double myHeight;

	/**
	 * The width of the rendering object
	 */
	private double myWidth;

	private double[] myMargins = new double[4];

	private INode myNode;

	private IBookPainter myPainter;

	protected AbstractRenderingObject(IBookPainter painter, INode node) {
		this.myPainter = painter;
		this.myNode = node;
	}

	public double getHeight() {
		return this.myHeight;
	}

	public double getWidth() {
		return this.myWidth;
	}

	protected void setHeight(double height) {
		this.myHeight = height;
	}

	protected void setWidth(double width) {
		this.myWidth = width;
	}

	public INode getNode() {
		return this.myNode;
	}

	public IBookPainter getPainter() {
		return this.myPainter;
	}
	
	public final void render() {
		this.getPainter().addHorizontalStrut(this.myMargins[BoxSides.LEFT]);
		renderContents();
		this.getPainter().addHorizontalStrut( - this.myMargins[BoxSides.LEFT]);
	}

	public abstract void renderContents();

	public void setMargins(double top, double right, double bottom, double left) {
		this.myMargins[BoxSides.TOP] = top;
		this.myMargins[BoxSides.RIGHT] = right;
		this.myMargins[BoxSides.BOTTOM] = bottom;
		this.myMargins[BoxSides.LEFT] = left;
	}

	public boolean isBrokeable() {
		return false;
	}

}
