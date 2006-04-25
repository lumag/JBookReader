package org.jbookreader.book.stylesheet;

/**
 * This interface represents styling information.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public interface IStyleSheet {
	/**
	 * Returns new style stack.
	 * @return new style stack.
	 */
	IStyleStack newStyleStateStack();
}
