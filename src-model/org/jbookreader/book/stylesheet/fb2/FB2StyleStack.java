package org.jbookreader.book.stylesheet.fb2;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.jbookreader.book.stylesheet.IStyleStack;
import org.jbookreader.book.stylesheet.properties.EDisplayType;

class FB2StyleStack implements IStyleStack {
	private List<SimpleNode> myNodesList = new ArrayList<SimpleNode>();
	
	public void pushTag(String name, String klass, String id) {
		this.myNodesList.add(new SimpleNode(name, klass, id));
	}

	public void popTag() {
		this.myNodesList.remove(this.myNodesList.size()-1);
	}

	public EDisplayType getDisplay() {
		ListIterator<SimpleNode> iterator = this.myNodesList.listIterator(this.myNodesList.size());
		
		SimpleNode node = iterator.previous();

		// special handling for images
		if (node.name.equals("image")) {
			while (iterator.hasPrevious()) {
				SimpleNode cnode = iterator.previous();
				if (FB2StyleSheet.getDisplay(cnode.name) == EDisplayType.INLINE)
					return EDisplayType.INLINE;
				if (cnode.name.equals("p") ||
				    cnode.name.equals("v") ||
				    cnode.name.equals("subtitle") ||
				    cnode.name.equals("text-author") ||
				    cnode.name.equals("coverpage")) {
					return EDisplayType.INLINE;
				}
			}
			return EDisplayType.BLOCK;
		}

		EDisplayType result = FB2StyleSheet.getDisplay(node.name);
		if (result != null) {
			return result;
		}

		System.err.println("Got unknown tag: '"+ node.name + "'! Check your parser version, please");

		// sane default
		return EDisplayType.BLOCK;
	}

	public double getMarginLeft() {
		return 5;
	}

	public double getMarginRight() {
		return 5;
	}

	public double getTextIndent() {
		return 5;
	}

	public double getLineHeight() {
		return getFontSize() * 1.2;
	}
	
	public String getFontFamily() {
		return "Serif";
	}
	
	public int getFontSize() {
		return 12;
	}

}
