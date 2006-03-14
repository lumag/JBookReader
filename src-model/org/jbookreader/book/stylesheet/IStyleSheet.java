package org.jbookreader.book.stylesheet;

import org.jbookreader.book.bom.INode;

/**
 * This interface represents styling information.
 * 
 * FIXME: provide real style information. see CSS1
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public interface IStyleSheet {
	EDisplayType getNodeDisplayType(INode node);
}
