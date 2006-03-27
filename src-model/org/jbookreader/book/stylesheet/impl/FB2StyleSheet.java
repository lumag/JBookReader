package org.jbookreader.book.stylesheet.impl;

import java.util.HashMap;
import java.util.Map;

import org.jbookreader.book.bom.IContainerNode;
import org.jbookreader.book.bom.IImageNode;
import org.jbookreader.book.bom.INode;
import org.jbookreader.book.stylesheet.EDisplayType;
import org.jbookreader.book.stylesheet.IStyleSheet;

/**
 * This is temporary class for returning statndard FB2 information.
 * 
 * FIXME: rewrite!
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class FB2StyleSheet implements IStyleSheet {
	
	/**
	 * This mapping holds tagName -> display type information.
	 */
	private Map<String, EDisplayType> myDisplayTypes = new HashMap<String,EDisplayType>();
	
	/**
	 * These are <code>{display: block;}</code> tags.
	 */
	private static final String[] FB2_BLOCK_TAGS = {
		"body",
		"section",
		"title",
		"p", "v",
		"empty-line",
		"abstract",
		"epigraph"
		};
	
	/**
	 * These are <code>{display: inline;}</code> tags.
	 */
	private static final String[] FB2_INLINE_TAGS = {
		"strong",
		"emphasis",
		"strikethrough",
		"sub",
		"sup",
		"code",
		"a",
		"#text"
		};
	
	/**
	 * Constructs the stylesheet by filling information in internal structures.
	 *
	 */
	public FB2StyleSheet() {
		for (String s: FB2_INLINE_TAGS) {
			this.myDisplayTypes.put(s, EDisplayType.INLINE);
		}

		for (String s: FB2_BLOCK_TAGS) {
			this.myDisplayTypes.put(s, EDisplayType.BLOCK);
		}
	}

	public EDisplayType getNodeDisplayType(INode node) {
		if (this.myDisplayTypes.containsKey(node.getTagName()))
			return this.myDisplayTypes.get(node.getTagName());
		
		// special handling for images
		if (node instanceof IImageNode) {
			IContainerNode cnode = node.getParentNode();
			while (cnode != null) {
				if (getNodeDisplayType(cnode) == EDisplayType.INLINE)
					return EDisplayType.INLINE;
				if (cnode.getTagName().equals("p") ||
				    cnode.getTagName().equals("v") ||
				    cnode.getTagName().equals("subtitle") ||
				    cnode.getTagName().equals("text-author") ||
				    cnode.getTagName().equals("coverpage")) {
					return EDisplayType.INLINE;
				}
				cnode = cnode.getParentNode();
			}
			return EDisplayType.BLOCK;
		}

		System.err.println("Got unknown tag: '"+ node.getTagName() + "'! Check your parser version, please");

		// sane default
		return EDisplayType.BLOCK;
	}

	public double getLeftMargin(INode node) {
		// TODO Auto-generated method stub
		return 5;
	}

	public double getFirstLineMargin(INode node) {
		// TODO Auto-generated method stub
		return 10;
	}

	public double getRightMargin(INode node) {
		// TODO Auto-generated method stub
		return 5;
	}

}
