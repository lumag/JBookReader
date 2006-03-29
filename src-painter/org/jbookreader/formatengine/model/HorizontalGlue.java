package org.jbookreader.formatengine.model;

import org.jbookreader.formatengine.IBookPainter;
import org.jbookreader.formatengine.IRenderingObject;
import org.jbookreader.formatengine.RenderingDimensions;

/**
 * This class represents horizontal glue rendering object (whitespace).
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class HorizontalGlue implements IRenderingObject {

	/**
	 * The size of the strut.
	 */
	private double myStrut;
	/**
	 * The corresponding text painter
	 */
	private IBookPainter myTextPainter;

	/**
	 * This constructs new horizontal glue with specified parameters.
	 * @param strut the size of glue object
	 * @param painter the painter for this object
	 */
	public HorizontalGlue(double strut, IBookPainter painter) {
		this.myStrut = strut;
		this.myTextPainter = painter;
	}

	public RenderingDimensions getDimensions() {
		return new RenderingDimensions(0, 0, this.myStrut);
	}

	public void render() {
		this.myTextPainter.addHorizontalStrut(this.myStrut);
	}

	public boolean isGlue() {
		return true;
	}

}
