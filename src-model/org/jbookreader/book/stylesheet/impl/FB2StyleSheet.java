package org.jbookreader.book.stylesheet.impl;

import java.util.HashMap;
import java.util.Map;

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
	
	private Map<String, EDisplayType> myDisplayTypes = new HashMap<String,EDisplayType>();
	
	private static final String[] FB2_BLOCK_TAGS = {
		"body",
		"section",
		"title",
		"p",
		"empty-line"
		};
	
	private static final String[] FB2_INLINE_TAGS = {
		"strong",
		"emphasis",
		"strikethrough",
		"sub",
		"sup",
		"code",
		"#text"
		};
	
	public FB2StyleSheet() {
		for (String s: FB2_INLINE_TAGS) {
			this.myDisplayTypes.put(s, EDisplayType.INLINE);
		}

		for (String s: FB2_BLOCK_TAGS) {
			this.myDisplayTypes.put(s, EDisplayType.BLOCK);
		}
	}

	public EDisplayType getNodeDisplayType(INode node) throws IllegalStateException {
		if (this.myDisplayTypes.containsKey(node.getTagName()))
			return this.myDisplayTypes.get(node.getTagName());
		throw new IllegalStateException("Got unknown tag! Check your parser version, please");
	}

}
