package org.jbookreader.book.bom.internal;

import org.jbookreader.book.bom.AbstractContainerNode;
import org.jbookreader.book.stylesheet.EDisplayType;

/**
 * This class represents very simple container.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class ContainerNode extends AbstractContainerNode {
	private EDisplayType myDisplayType = EDisplayType.INLINE;

	public EDisplayType getDisplayType() {
		return this.myDisplayType;
	}

	public void setDisplayType(EDisplayType displayType) {
		this.myDisplayType = displayType;
	}

}
