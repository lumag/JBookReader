package org.jbookreader.book;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jbookreader.book.bom.BodyNode;
import org.jbookreader.book.bom.INode;

/**
 * This class represents a single book as a triplet of book metatada, book bodies and binary blobs.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class Book implements Iterable<BodyNode> {
	/**
	 * The list with book bodies.
	 */
	private List<BodyNode> myBodies = new LinkedList<BodyNode>();
	/**
	 * Maps id -> node.
	 */
	private Map<String, INode> myIDmap = new LinkedHashMap<String, INode>();
	/**
	 * The list with binary blobs, encapsulated in the book.
	 */
	private Map<String, BinaryData> myBinaries = new LinkedHashMap<String, BinaryData>();
	
	/**
	 * Adds a new  body to the list of bodies in the book.
	 * @param body the new body to add
	 */
	public void addBody(BodyNode body) {
		this.myBodies.add(body);
	}

	/**
	 * Returns the main (the first) body of the book.
	 * @return the main body of the book
	 */
	public BodyNode getMainBody() {
		if (this.myBodies.size() > 0) {
			return this.myBodies.get(0);
		}
		throw new IllegalStateException("No main body provided");
	}
	
	public Iterator<BodyNode> iterator() {
		return this.myBodies.iterator();
	}
	
	/**
	 * Adds binary blob.
	 * @param bdata binary data
	 */
	public void addBinaryData(BinaryData bdata) {
		this.myBinaries.put(bdata.getID(), bdata);
	}
	
	/**
	 * Returns the binary blob associated with specified <code>id</code>.
	 * If no corresponding blob is found, returns <code>null</code>.
	 * @param id the id of the blob
	 * @return the binary blob associated with specified id.
	 */
	public BinaryData getBinaryData(String id) {
		return this.myBinaries.get(id);
	}
	
	/**
	 * Returns a node associated with specified <code>id</code>
	 * @param id the ID of the node
	 * @return a node associated with <code>id</code>.
	 */
	public INode getNodeByID(String id) {
		return this.myIDmap.get(id);
	}
	
	/**
	 * This sets the id -> node mapping
	 * @param id the ID of the node
	 * @param node the node
	 */
	@SuppressWarnings("deprecation")
	public void setNodeID(String id, INode node) {
		node.setID(id);
		this.myIDmap.put(id, node);
	}
}
