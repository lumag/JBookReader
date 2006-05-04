package org.jbookreader.book.stylesheet;

import org.jbookreader.book.stylesheet.properties.EDisplayType;

/**
 * This interface represents a style stack &mdash; a model
 * of cascading styling information.
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public interface IStyleStack {
	/**
	 * Pushes new tag into the style stack.
	 * 
	 * @param name the name of the tag
	 * @param klass the class of the tag
	 * @param id the id of the tag
	 */
	void pushTag(String name, String klass, String id);

	/**
	 * Pops last tag from the style stack.
	 *
	 */
	void popTag();


	/**
	 * Returns the 'display' property of the node
	 * @return display type of the node.
	 */
	EDisplayType getDisplay();
	
	/**
	 * Returns the left margin for the node.
	 * @return the left margin for the node.
	 */
	double getMarginLeft();
	/**
	 * Returns the right margin for the node.
	 * @return the right margin for the node.
	 */
	double getMarginRight();
	/**
	 * Returns the indentation for the first line of the node.
	 * @return the indentation for the first line of the node.
	 */
	double getTextIndent();
	/**
	 * Returns the relative line height.
	 * @return the relative line height.
	 */
	double getLineHeight();
	/**
	 * Returns font family.
	 * @return font family.
	 */
	String getFontFamily();
	/**
	 * Returns font size.
	 * @return font size.
	 */
	int getFontSize();

	/**
	 * Sets default font family.
	 * 
	 * @param fontFamily default font family
	 */
	void setDefaultFontFamily(String fontFamily);
	
	/**
	 * Sets default font size.
	 * 
	 * @param size default font size
	 */
	void setDefaultFontSize(int size);
	
}
