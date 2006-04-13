package org.jbookreader.formatengine.model;

import org.jbookreader.book.bom.INode;
import org.jbookreader.formatengine.AbstractRenderingObject;
import org.jbookreader.formatengine.IBookPainter;

/**
 * This class represents horizontal glue rendering object (whitespace).
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 * 
 */
public class HorizontalGlue extends AbstractRenderingObject {

	/**
	 * This constructs new horizontal glue with specified parameters.
	 * 
	 * @param strut the size of glue object
	 * @param painter the painter for this object
	 */
	public HorizontalGlue(IBookPainter painter, INode node, double strut) {
		super(painter, node);
		setWidth(strut);
	}

	public void render() {
		this.getPainter().addHorizontalStrut(this.getWidth());
	}

}
