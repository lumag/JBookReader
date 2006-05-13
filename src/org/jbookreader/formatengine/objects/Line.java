package org.jbookreader.formatengine.objects;

import java.util.LinkedList;
import java.util.List;

import org.jbookreader.book.bom.INode;
import org.jbookreader.formatengine.IBookPainter;
import org.jbookreader.formatengine.IInlineRenderingObject;

/**
 * This class represents the main object of rendering engine &mdash; a single line.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 * 
 */
public class Line extends AbstractInlineRenderingObject {
	/**
	 * The list of contained rendered objects.
	 */
	private final List<IInlineRenderingObject> myRObjects = new LinkedList<IInlineRenderingObject>();
	
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
	public void addObject(IInlineRenderingObject object) {
		double oldOver = this.getHeight() - this.getDepth();
		double newOver = object.getHeight() - object.getDepth();
		
		this.myRObjects.add(object);
		
		double oDepth = object.getDepth();
		
		if (oDepth > this.getDepth()) {
			this.setDepth(oDepth);
		}
		
		if (newOver > oldOver) {
			setHeight(newOver + this.getDepth());
		}

		setWidth(getWidth() + object.getWidth());
	}

	public void renderInline() {
//		IBookPainter painter = this.getPainter();
//		double y = painter.getYCoordinate() + getDepth();
//		if (y > painter.getHeight()
//			|| y < getHeight()) {
//			painter.addHorizontalStrut(getWidth());
//			return;
//		}
//
		for (IInlineRenderingObject robject : this.myRObjects) {
			robject.renderInline();
		}
	}
}
