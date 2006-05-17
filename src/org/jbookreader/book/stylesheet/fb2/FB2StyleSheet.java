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
package org.jbookreader.book.stylesheet.fb2;

import java.util.HashMap;
import java.util.Map;

import org.jbookreader.book.stylesheet.EDisplayType;
import org.jbookreader.book.stylesheet.IStyleSheet;
import org.jbookreader.book.stylesheet.IStyleStack;

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
	 * This mapping holds tagName -&gt; display type information.
	 */
	private static Map<String, EDisplayType> ourDisplayTypes = new HashMap<String,EDisplayType>();
	
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
		"stanza",
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
	
	static {
		for (String s: FB2_INLINE_TAGS) {
			ourDisplayTypes.put(s, EDisplayType.INLINE);
		}

		for (String s: FB2_BLOCK_TAGS) {
			ourDisplayTypes.put(s, EDisplayType.BLOCK);
		}
	}

	static EDisplayType getDisplay(String name) {
		return ourDisplayTypes.get(name);
	}

	public IStyleStack newStyleStateStack() {
		return new FB2StyleStack();
	}

}
