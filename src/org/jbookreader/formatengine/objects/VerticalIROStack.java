package org.jbookreader.formatengine.objects;

import java.util.ArrayList;
import java.util.List;

import org.jbookreader.book.bom.INode;
import org.jbookreader.formatengine.IBookPainter;
import org.jbookreader.formatengine.IRenderingObject;

public class VerticalIROStack extends AbstractRenderingObject implements
		IRenderingObject {
	
	private List<IRenderingObject> myObjects = new ArrayList<IRenderingObject>();
	
	public VerticalIROStack(IBookPainter painter, INode node) {
		super(painter, node);
	}

	@Override
	public void renderContents() {
		for (IRenderingObject object: this.myObjects) {
			object.render();
		}
	}

	public void addObject(IRenderingObject object) {
		double width = object.getWidth();

		if (getWidth() < width) {
			setWidth(width);
		}

		setHeight(getHeight() + object.getHeight());
		
		this.myObjects.add(object);
	}
}
