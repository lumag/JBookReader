package org.jbookreader.book.stylesheet;

/**
 * This enum contains values corresponding to the 'display' CSS property.
 * They control how the display of the node is handled and displayed on the
 * canvas during display.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 * XXX: add if necessary list-item and table-related ones?
 */
public enum EDisplayType {
	/**
	 * A new box.
	 */
	BLOCK,
	/**
	 * New box on the same line, as the previous context.
	 */
	INLINE,
	/**
	 * Not displayed. Child items aren't displayed too.
	 */
	NONE;
}
