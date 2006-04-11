package org.jbookreader.book.stylesheet;

import org.jbookreader.book.stylesheet.properties.EDisplayType;

public interface IStyleStack {
	void pushTag(String name, String klass, String id);
	void popTag();


	/**
	 * Returns the 'display' property of the node
	 * @param node the node to check
	 * @return display type of the node.
	 */
	EDisplayType getDisplay();
	
	/**
	 * Returns the left margin for the node.
	 * @param node the node to get syuling information for
	 * @return the left margin for the node.
	 */
	double getMarginLeft();
	/**
	 * Returns the right margin for the node.
	 * @param node the node to get syuling information for
	 * @return the right margin for the node.
	 */
	double getMarginRight();
	/**
	 * Returns the indentation for the first line of the node.
	 * @param node the node to get syuling information for
	 * @return the indentation for the first line of the node.
	 */
	double getTextIndent();
}
