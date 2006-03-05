package org.jbookreader.book.stylesheet;

/**
 * This enum contains the various types of text styles.
 * XXX: think about styleType and linkType  
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public enum ETextStyle {
	/** bold */
	STRONG,
	/** italic */
	EMPHASIS,
	/** stroken through */
	STRIKETHROUGH,
	/** subscript */
	SUBSCRIPT,
	/** superscript */
	SUPERSCRIPT,
	/** typewriter */
	CODE,
	/** special style. Use for misc./unspecified styles. */
	SPECIAL;
}
