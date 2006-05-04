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
	/**
	 * The height of the rendering object above the baseline.
	 */
	private double myHeight;

	/**
	 * The depth of the rendering object below the baseline.
	 */
	private double myDepth;

	/**
	 * The width of the rendering object
	 */
	private double myWidth;

	private INode myNode;

	private IBookPainter myPainter;

	protected AbstractRenderingObject(IBookPainter painter, INode node) {
		this.myPainter = painter;
		this.myNode = node;
	}

	public double getDepth() {
		return this.myDepth;
	}

	public double getHeight() {
		return this.myHeight;
	}

	public double getWidth() {
		return this.myWidth;
	}

	protected void setDepth(double depth) {
		this.myDepth = depth;
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

}
