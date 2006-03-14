package org.jbookreader.book.bom;

import java.util.Collection;

/**
 * This interface represents the main part of the BookObjectModel &mdash; the book itself.
 * 
 * The book consists of metadata, one or more bodies and maybe some binary data.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public interface IBook {

	/**
	 * Creates new body node with give tag and node names.
	 * @param tagName the tag name
	 * @param name the name of new body or null if it's main node.
	 * @return new body node.
	 */
	ISectioningNode newBody(String tagName, String name);

	/**
	 * Returns the main (the first) body of the book.
	 * @return the main body of the book
	 */
	ISectioningNode getMainBody();

	/**
	 * Returns a collection of book bodies.
	 * @return a collection of book bodies.
	 */
	Collection<ISectioningNode> getBodies();

	/**
	 * Allocates new binary data.
	 * @param id the id of given data
	 * @param contentType the content type of the data.
	 * @return new binary blob object.
	 */
	IBinaryData newBinaryData(String id, String contentType);

	/**
	 * Returns the binary blob associated with specified <code>id</code>.
	 * If no corresponding blob is found, returns <code>null</code>.
	 * @param id the id of the blob
	 * @return the binary blob associated with specified id.
	 */
	IBinaryData getBinaryData(String id);

}