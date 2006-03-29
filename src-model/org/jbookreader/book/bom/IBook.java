package org.jbookreader.book.bom;

import java.util.Collection;
import java.util.Map;

import org.jbookreader.book.stylesheet.IStyleSheet;

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
	IContainerNode newBody(String tagName, String name);

	/**
	 * Returns the main (the first) body of the book.
	 * @return the main body of the book
	 */
	IContainerNode getMainBody();

	/**
	 * Returns a collection of book bodies.
	 * @return a collection of book bodies.
	 */
	Collection<IContainerNode> getBodies();

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

	/**
	 * Returns an id-&gt;binary data mapping.
	 * @return an id-&gt;binary data mapping.
	 */
	Map<String, ? extends IBinaryData> getBinaryMap();

	/**
	 * Returns the system stylesheet for specified book.
	 * @return the system stylesheet for specified book.
	 */
	IStyleSheet getSystemStyleSheet();
	
	/**
	 * Don't use this for now.
	 * @param stylesheet
	 */
	void setSystemStyleSheet(IStyleSheet stylesheet);
	
	/**
	 * Returns the node with specified ID.
	 * @param id the id of node
	 * @return the node with specified ID.
	 */
	INode getNodeByID(String id);
}