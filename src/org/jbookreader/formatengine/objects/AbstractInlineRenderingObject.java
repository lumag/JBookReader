package org.jbookreader.formatengine.objects;

import org.jbookreader.book.bom.INode;
import org.jbookreader.formatengine.IBookPainter;
import org.jbookreader.formatengine.IInlineRenderingObject;

public abstract class AbstractInlineRenderingObject extends
		AbstractRenderingObject implements IInlineRenderingObject {
	
	private double myDepth;

	public AbstractInlineRenderingObject(IBookPainter painter, INode node) {
		super(painter, node);
	}

	public double getDepth() {
		return this.myDepth;
	}

	protected void setDepth(double depth) {
		this.myDepth = depth;
	}
	
	@Override
	public final void renderContents() {
		IBookPainter painter = getPainter();
		painter.addVerticalStrut(getHeight() - getDepth());
		renderInline();
		painter.addHorizontalStrut(-getWidth());
		painter.addVerticalStrut(getDepth());
	}

}
