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
	/**
	 * Returns the 'display' property of the node
	 * @param node the node to check
	 * @return display type of the node.
	 */
	EDisplayType getNodeDisplayType(INode node);
	
	double getLeftMargin(INode node);
	double getFirstLineMargin(INode node);
	double getRightMargin(INode node);
}
