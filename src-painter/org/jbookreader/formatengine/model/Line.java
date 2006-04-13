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
	 * 
	 * @param object the object to add
	 */
	public void addObject(IRenderingObject object) {
		this.myRObjects.add(object);
		if (object.getHeight() > getHeight()) {
			setHeight(object.getHeight());
		}

		if (object.getDepth() > this.getDepth()) {
			setDepth(object.getDepth());
		}

		setWidth(getWidth() + object.getWidth());

	}

	public void render() {
		for (IRenderingObject robject : this.myRObjects) {
			robject.render();
		}
	}

	public List<IRenderingObject> getObjects() {
		return Collections.unmodifiableList(this.myRObjects);
	}

}
