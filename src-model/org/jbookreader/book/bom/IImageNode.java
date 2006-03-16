package org.jbookreader.book.bom;

/**
 * This interface represents image node.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public interface IImageNode extends INode {

	/**
	 * Returns the location of the image. Probably you should only use internal locations ('#id') for now.
	 * @return the location of the image.
	 */
	String getHyperRef();

	/**
	 * Returns the title of the image or null if there is no title.
	 * @return the title of the image
	 */
	String getTitle();

}