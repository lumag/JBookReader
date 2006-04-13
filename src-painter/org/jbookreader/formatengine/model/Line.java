package org.jbookreader.formatengine.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jbookreader.book.bom.INode;
import org.jbookreader.formatengine.AbstractRenderingObject;
import org.jbookreader.formatengine.IBookPainter;
import org.jbookreader.formatengine.IRenderingObject;

/**
 * This class represents the main object of rendering engine &mdash; the line of
 * text.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 * 
 */
public class Line extends AbstractRenderingObject {
	/**
	 * The list of contained rendered objects.
	 */
	private final List<IRenderingObject> myRObjects = new LinkedList<IRenderingObject>();


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
		double over = getHeight() - getDepth();
		for (IRenderingObject robject : this.myRObjects) {
			if (robject.getHeight() != 0) {
				double newOver = robject.getHeight() - robject.getDepth();
				double vstrut = over - newOver;
				if (vstrut != 0) {
					this.getPainter().addVerticalStrut(vstrut);
					over = newOver;
				}
			}
			robject.render();
		}
		this.getPainter().addVerticalStrut(over - (getHeight() - getDepth()));
	}

	public List<IRenderingObject> getObjects() {
		return Collections.unmodifiableList(this.myRObjects);
	}

}
