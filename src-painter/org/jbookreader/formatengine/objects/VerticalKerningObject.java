package org.jbookreader.formatengine.objects;

import org.jbookreader.book.bom.INode;
import org.jbookreader.formatengine.AbstractRenderingObject;
import org.jbookreader.formatengine.IBookPainter;

/**
 * Vertical kerning object. Don't use outside of {@link Line}.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class VerticalGlue extends AbstractRenderingObject {
	
	public VerticalGlue(IBookPainter painter, INode node, double strut) {
		super(painter, node);
		if (strut > 0) {
			setHeight(strut);
		} else {
			setDepth(-strut);
		}
	}

	public void render() {
		this.getPainter().addVerticalStrut(this.getHeight()-this.getDepth());
	}

}
