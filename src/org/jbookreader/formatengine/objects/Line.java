package org.jbookreader.formatengine.objects;

import java.util.LinkedList;
import java.util.List;

import org.jbookreader.book.bom.INode;
import org.jbookreader.formatengine.AbstractRenderingObject;
import org.jbookreader.formatengine.IBookPainter;
import org.jbookreader.formatengine.IRenderingObject;

/**
 * This class represents the main object of rendering engine &mdash; a sibgle line.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 * 
 */
public class Line extends AbstractRenderingObject {
	/**
	 * The list of contained rendered objects.
	 */
	private final List<IRenderingObject> myRObjects = new LinkedList<IRenderingObject>();
	
	private double myLeftMargin;

	/**
	 * This constructs new line for specified painter.
	 * @param painter the painter on which this line will be rendered
	 * @param node the paragraph node to which the line corresponds
	 */
	public Line(IBookPainter painter, INode node) {
		super(painter, node);
	}

	/**
	 * Adds a rendering object to the line.
	 * TODO: Vertical align support 
	 * 
	 * @param object the object to add
	 */
	public void addObject(IRenderingObject object) {
		double oldOver = this.getHeight() - this.getDepth();
		double newOver = object.getHeight() - object.getDepth();
		
		this.myRObjects.add(object);
		
		if (object.getDepth() > this.getDepth()) {
			setDepth(object.getDepth());
		}
		
		if (newOver > oldOver) {
			setHeight(newOver + getDepth());
		}

		setWidth(getWidth() + object.getWidth());
	}

	public void render() {
		for (IRenderingObject robject : this.myRObjects) {
			robject.render();
		}
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
	 *
	 * @param leftMargin new left margin value.
	 */
	public void setLeftMargin(double leftMargin) {
		this.myLeftMargin = leftMargin;
	}

}
