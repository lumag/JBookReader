package org.jbookreader.formatengine;

import org.jbookreader.book.bom.IBook;
import org.jbookreader.book.bom.IContainerNode;
import org.jbookreader.book.bom.INode;

public class FormatEngine {
	private ITextPainter myPainter;
	private IBook myBook;

	public ITextPainter getPainter() {
		return this.myPainter;
	}

	public void setPainter(ITextPainter painter) {
		this.myPainter = painter;
	}

	public IBook getBook() {
		return this.myBook;
	}

	public void setBook(IBook book) {
		this.myBook = book;
	}
	
	private int consumeWhitespace(String text, int start) {
		while ((start < text.length()) && (text.charAt(start) <= '\u0020'))
			start ++;
		return start;
	}

	private int pushString(String text) {
		int start = 0, end = 0;
		double xpos = this.myPainter.getXPosition();
		ITextFont font = null;

		while (end < text.length()) {
			int temp;
			temp = consumeWhitespace(text, start);
			if (temp > start) {
				// FIXME: correct space size!
				double strut = 1;
				
				if (xpos + strut < this.myPainter.getWidth()) {
					this.myPainter.addHorizontalStrut(strut);
					xpos += strut;
				} else {
					// FIXME: vertical strut calculation
					this.myPainter.flushString(0);
					xpos = 0;
				}
			}

			end = start = temp;
			if (end >= text.length())
				return -1;

			while ((end < text.length()) && (text.charAt(end) > '\u0020')) {
				end ++;
			}
			
//			end --;
			
			StringDimensions dim = this.myPainter.calculateStringDimensions(text, start, end, font);
			
			// FIXME: this is the main place for rendering decision
			if (xpos + dim.width > this.myPainter.getWidth()) {
				// FIXME: vstrut calculation
				this.myPainter.flushString(0);
				xpos = 0;
			}
			this.myPainter.renderString(text, start, end, font);
			xpos += dim.width;
			start = end;
		}
		return -1;
	}
	
	private void renderNode(INode node) {
		if (node.isContainer()) {
			IContainerNode cnode = (IContainerNode)node;
			for (INode child : cnode.getChildNodes())
				renderNode(child);
		} else {
			pushString(node.getText());
		}
		
	}
	
	// FIXME: think about returning positions, etc.
	public void renderPage() {
		// FIXME!
		renderNode(this.myBook.getMainBody());
	}
}
