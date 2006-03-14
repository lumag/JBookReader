package org.jbookreader.book.bom.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jbookreader.book.bom.IBinaryData;
import org.jbookreader.book.bom.IBook;
import org.jbookreader.book.bom.ISectioningNode;
import org.jbookreader.book.stylesheet.IStyleSheet;


/**
 * This class represents a single book as a triplet of book metatada, book bodies and binary blobs.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class Book implements IBook {
	private IStyleSheet mySystemStyleSheet;
	/**
	 * The list with book bodies.
	 */
	private Map<String, ISectioningNode> myBodies = new LinkedHashMap<String, ISectioningNode>();
	/**
	 * Maps id -> node.
	 */
//	private Map<String, INode> myIDmap = new LinkedHashMap<String, INode>();
	/**
	 * The list with binary blobs, encapsulated in the book.
	 */
	private Map<String, BinaryData> myBinaries = new LinkedHashMap<String, BinaryData>();
	
	public ISectioningNode newBody(String tagName, String name) {
		SectioningNode body = new SectioningNode();
		body.setTagName(tagName);
		body.setBook(this);
		this.myBodies.put(name, body);
		return body;
	}

	public ISectioningNode getMainBody() {
		ISectioningNode node = this.myBodies.get(null);
		if (node == null)
			throw new IllegalStateException("No main body provided");
		return node;
	}
	
	public Collection<ISectioningNode> getBodies() {
		return Collections.unmodifiableCollection(this.myBodies.values());
	}
	
	public IBinaryData newBinaryData(String id, String contentType) {
		BinaryData bdata = new BinaryData();
		bdata.setContentType(contentType);
		this.myBinaries.put(id, bdata);
		return bdata;
	}
	
	public IBinaryData getBinaryData(String id) {
		return this.myBinaries.get(id);
	}

	public IStyleSheet getSystemStyleSheet() {
		return this.mySystemStyleSheet;
	}

	public void setSystemStyleSheet(IStyleSheet systemStyleSheet) {
		this.mySystemStyleSheet = systemStyleSheet;
	}

	/**
	 * Returns a node associated with specified <code>id</code>
	 * @param id the ID of the node
	 * @return a node associated with <code>id</code>.
	 */
//	public INode getNodeByID(String id) {
//		return this.myIDmap.get(id);
//	}

}
