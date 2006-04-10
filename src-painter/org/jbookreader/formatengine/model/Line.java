package org.jbookreader.formatengine.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jbookreader.book.bom.INode;
import org.jbookreader.formatengine.IRenderingObject;
import org.jbookreader.formatengine.RenderingDimensions;

/**
 * This class represents the main object of rendering engine &mdash; the line of text
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class Line {
	/**
	 * The list of contained rendered objects.
	 */
	private final List<IRenderingObject> myRObjects = new LinkedList<IRenderingObject>();
	/**
	 * The left margin of the line.
	 */
	private double myLeftMargin;
	/**
	 * The right margin of the line.
	 */
	private double myRightMargin;
	/**
	 * The height of the line above the baseline.
	 */
	private double myHeight;
	/**
	 * The depth of the line below the baseline.
	 */
	private double myDepth;
	/**
	 * The width of the line
	 */
	private double myWidth;
	/**
	 * True if this line is the first line of the paragraph.
	 */
	private boolean myFirstLine;
	/**
	 * The node containing the pragraph.
	 */
	private INode myParagraphNode;
	
	/**
	 * Constructs the new line with specified paragraph node.
	 * @param firstLine true if this is the first line
	 * @param node the corresponding node for the paragraph.
	 */
	public Line(boolean firstLine, INode node) {
		this.myFirstLine = firstLine;
		this.myParagraphNode = node;
	}

	/**
	 * Adds a rendering object to the line.
	 * @param object the object to add
	 */
	public void addObject(IRenderingObject object) {
		this.myRObjects.add(object);
		RenderingDimensions roDimensions = object.getDimensions();
		if (roDimensions.height > this.myHeight) {
			this.myHeight = roDimensions.height;
		}
		
		if (roDimensions.depth > this.myDepth) {
			this.myDepth = roDimensions.depth;
		}
		
		this.myWidth += roDimensions.width;
	}
	
	/**
	 * Returns a  list of the objects in the line.
	 * @return a  list of the objects in the line.
	 */
	public List<IRenderingObject> getObjects() {
		return Collections.unmodifiableList(this.myRObjects);
	}

	/**
	 * Returns the left margin of the line.
	 * @return the left margin of the line.
	 */
	public double getLeftMargin() {
		return this.myLeftMargin;
	}
	
	/**
	 * Sets the left margin of the line.
	 * @param margin the left margin of the line
	 */
	public void setLeftMargin(double margin) {
		this.myLeftMargin = margin;
	}

	/**
	 * Returns the right margin of the line.
	 * @return the right margin of the line.
	 */
	public double getRightMargin() {
		return this.myRightMargin;
	}

	/**
	 * Sets the right margin of the line.
	 * @param rightMargin the right margin of the line.
	 */
	public void setRightMargin(double rightMargin) {
		this.myRightMargin = rightMargin;
	}

	/**
	 * Returns the height of the line over the baseline.
	 * @return the height of the line over the baseline.
	 */
	public double getHeight() {
		return this.myHeight;
	}

	/**
	 * Returns the depth of the line below the baseline.
	 * @return the depth of the line below the baseline.
	 */
	public double getDepth() {
		return this.myDepth;
	}

	/**
	 * Returns the widthe of the line.
	 * @return the widthe of the line.
	 */
	public double getWidth() {
		return this.myWidth;
	}
	
	/**
	 * Returns the corresponding paragraph node.
	 * @return the corresponding paragraph node.
	 */
	public INode getParagraphNode() {
		return this.myParagraphNode;
	}

	/**
	 * True if this is the first line of the paragraph
	 * @return where this is the first line of the paragraph.
	 */
	public boolean isFirstLine() {
		return this.myFirstLine;
	}

}
