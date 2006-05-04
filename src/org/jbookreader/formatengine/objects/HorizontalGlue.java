package org.jbookreader.formatengine.objects;

import org.jbookreader.book.bom.INode;
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
	 * @param node the node corresponding to this glue object
	 */
	public HorizontalGlue(IBookPainter painter, INode node, double strut) {
		this(painter, node, strut, 0);
	}

	/**
	 * This constructs new horizontal glue with specified parameters.
	 * 
	 * @param strut the size of glue object
	 * @param painter the painter for this object
	 * @param node the node corresponding to this glue object
	 * @param height the height of the glue object
	 */
	public HorizontalGlue(IBookPainter painter, INode node, double strut, double height) {
		super(painter, node);
		setWidth(strut);
		setHeight(height);
	}

	public void render() {
		this.getPainter().addHorizontalStrut(this.getWidth());
	}

}
