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
	
	/**
	 * Returns the left margin for the node.
	 * @param node the node to get syuling information for
	 * @return the left margin for the node.
	 */
	double getLeftMargin(INode node);
	/**
	 * Returns the left margin for the first line of the node.
	 * @param node the node to get syuling information for
	 * @return the left margin for the first line of the node.
	 */
	double getFirstLineMargin(INode node);
	/**
	 * Returns the right margin for the node.
	 * @param node the node to get syuling information for
	 * @return the right margin for the node.
	 */
	double getRightMargin(INode node);
}
