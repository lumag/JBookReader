/*
 * JBookReader - Java FictionBook Reader
 * Copyright (C) 2006 Dmitry Baryshkov
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *   
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *   
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the Free Software
 *   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.jbookreader.book.bom.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jbookreader.book.bom.IBinaryData;
import org.jbookreader.book.bom.IBook;
import org.jbookreader.book.bom.IContainerNode;
import org.jbookreader.book.bom.INode;
import org.jbookreader.book.stylesheet.IStyleSheet;


/**
 * This class represents a single book as a triplet of book metatada, book bodies and binary blobs.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class Book implements IBook {
	/**
	 * The system stylesheet of the book.
	 */
	private IStyleSheet mySystemStyleSheet;
	/**
	 * The list with book bodies.
	 */
	private Map<String, IContainerNode> myBodies = new LinkedHashMap<String, IContainerNode>();
	/**
	 * Maps id -> node.
	 */
	private Map<String, INode> myIDmap = new LinkedHashMap<String, INode>();
	/**
	 * The list with binary blobs, encapsulated in the book.
	 */
	private Map<String, BinaryData> myBinaries = new LinkedHashMap<String, BinaryData>();
	
	public IContainerNode newBody(String tagName, String name) {
		ContainerNode body = new ContainerNode();
		body.setTagName(tagName);
		body.setBook(this);
		this.myBodies.put(name, body);
		return body;
	}

	public IContainerNode getMainBody() {
		IContainerNode node = this.myBodies.get(null);
		if (node == null) {
			throw new IllegalStateException("No main body provided");
		}
		return node;
	}
	
	public Collection<IContainerNode> getBodies() {
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
	
	public Map<String, ? extends IBinaryData> getBinaryMap() {
		return Collections.unmodifiableMap(this.myBinaries);
	}

	public IStyleSheet getSystemStyleSheet() {
		return this.mySystemStyleSheet;
	}

	public void setSystemStyleSheet(IStyleSheet systemStyleSheet) {
		this.mySystemStyleSheet = systemStyleSheet;
	}

	public INode getNodeByID(String id) {
		return this.myIDmap.get(id);
	}
	
	/**
	 * Adds a node to ID mapping. The node should already have correct ID.
	 * @param node the node to put into the mapping.
	 */
	void mapIdNode(INode node) {
		String id = node.getID();
		if (id != null) {
			this.myIDmap.put(id, node);
		}
	}

	public INode getNodeByReference(String reference) {
		String[] tokens = reference.split(";");
		INode node = null;
		for (String token: tokens) {
			// TODO: id handling
			int index = Integer.parseInt(token);
			// FIXME: find correct body!
			if (node == null) {
				node = getMainBody();
			} else if (node instanceof IContainerNode){
				List<INode> children = ((IContainerNode)node).getChildNodes();
				if (index < 0 || index >= children.size()) {
					System.err.println("Bad node reference: " + reference);
					return node;
				}
				node = children.get(index);
			} else {
				System.err.println("Bad node reference: " + reference);
				return node;
			}
		}
		return node;
	}

}
