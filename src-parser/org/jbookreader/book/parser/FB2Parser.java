package org.jbookreader.book.parser;

import java.io.IOException;

import org.jbookreader.book.bom.IBinaryData;
import org.jbookreader.book.bom.IBook;
import org.jbookreader.book.bom.IContainerNode;
import org.jbookreader.book.bom.ISectioningNode;
import org.jbookreader.book.bom.impl.Book;
import org.jbookreader.book.stylesheet.IStyleSheet;
import org.jbookreader.book.stylesheet.impl.FB2StyleSheet;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * This class represents a parser for the FB2 books format.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class FB2Parser {
	/**
	 * The namespace of FB2 tags
	 */
	public static final String FB2_XMLNS_URI = "http://www.gribuser.ru/xml/fictionbook/2.0";
	
	/**
	 * This parses the book located at the <code>uri</code>.
	 * @param uri the location of book to parse
	 * @return the parsed book representation
	 * @throws IOException in case of I/O problem
	 * @throws SAXException in case of XML parsing problem
	 */
	public static IBook parse(String uri) throws IOException, SAXException {
		XMLReader reader;
		
		reader = XMLReaderFactory.createXMLReader();
		reader.setErrorHandler(new ParseErrorHandler());

		IBook book = new Book();
		book.setSystemStyleSheet(getFB2StyleSheet());
		
		reader.setContentHandler(new FB2ContentsHandler(book));

		reader.parse(new InputSource(uri));

		return book;	
	}
	
	// FIXME: after finishing stylesheet loading, replace with reading of stylesheet.
	private static IStyleSheet ssheet;
	private static IStyleSheet getFB2StyleSheet() {
		if (ssheet == null) {
			ssheet = new FB2StyleSheet();
		}
		return ssheet;
	}

	/**
	 * This is a handler for SAX events for FB2 parser.
	 * 
	 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
	 *
	 */
	private static class FB2ContentsHandler extends DefaultHandler{
		/**
		 * The locator of the parser.
		 */
		private Locator myLocator;
		
		/**
		 * The book being parsed.
		 */
		private final IBook myBook;
		/**
		 * Binary blob data being parsed.
		 */
		private IBinaryData myBinaryData;

		/**
		 * The section being parsed.
		 */
		private ISectioningNode mySection;
		/**
		 * The container being parsed.
		 */
		private IContainerNode myContainer;

		/**
		 * Current text for #text nodes.
		 */
		private StringBuilder myText = new StringBuilder();
		
		/**
		 * Flag used for whitespace trimming.
		 */
		private boolean hadOpenTag = false;
		
		/**
		 * FIXME: remove this!!!!!
		 * it a workaround: we can't parseXML metadata currently. Only bodies and binary.
		 */
		private boolean parseXML = false;

		private boolean myParseText = false;
		
		/**
		 * This constructs new parser for given <code>book</code>
		 * @param book the book being parsed.
		 */
		public FB2ContentsHandler(IBook book) {
			this.myBook = book;
		}

		@Override
		public void setDocumentLocator(Locator locator) {
			this.myLocator = locator;
		}

		@Override
		public void startDocument() {
			System.out.println("Started parsing of '" + this.myLocator.getSystemId() + "'");
		}

		@Override
		public void endDocument() {
			System.out.println("Finished parsing of '" + this.myLocator.getSystemId() + "'");
		}

		@Override
		public void characters(char[] ch, int start, int length) {
			if (!this.parseXML)
				return;
			
			if (!this.myParseText)
				// FIXME: check if it's whitespace?
				return;
			
			this.myText.append(ch, start, length);
		}
		
		/**
		 * This processes information in <code>this.myText</code> into new #text node.
		 * @param hasCloseTag TODO
		 *
		 * @see FB2ContentsHandler#myText
		 */
		private void processTextNode(boolean hasCloseTag) {
			String string = trimStringBuilder(this.myText, this.hadOpenTag, hasCloseTag);
			this.myText.setLength(0);
			
			if (string.length() == 0) {
				return;
			}
			
			this.myContainer.newTextNode(string);

//			System.out.println("#text: '" + string + "'");
		}

		@Override
		public void endElement(String uri, String localName, String qName) {
			if (!this.parseXML)
				return;
			
			if (localName.equals("FictionBook")) {
				// XXX: root node;
			} else if (localName.equals("binary")) {
				this.myBinaryData.setBase64Encoded(this.myText.toString().toCharArray());
				this.myBinaryData = null;
			} else {
				// part of body
				processTextNode(true);
				this.hadOpenTag = false;
				
				if (localName.equals("p")) {
					this.myParseText = false;
				}
				
				if (this.myContainer.isSectioningNode()) {
					this.mySection = this.mySection.getParentSection();
				}
					
				this.myContainer = this.myContainer.getParentNode();

			}

			// System.out.println("</" + localName);
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXParseException {
			if (!this.parseXML) {
				if (!localName.equals("body")) {
					return;
				}
				System.out.println("Got first body tag");
				
				this.parseXML = true;
			}
			
			// System.out.println("<" + localName);

			processTextNode(false);
			this.hadOpenTag = true;
			
			if (localName.equals("FictionBook")) {
				// XXX: root book node
			} else if (localName.equals("binary")) {
				this.myBinaryData = this.myBook.newBinaryData(attributes.getValue("id"), attributes.getValue("content-type"));
			} else if (localName.equals("body")) {
				ISectioningNode node  = this.myBook.newBody("body", attributes.getValue("name"));

				this.myContainer = node;
				this.mySection = node;
			} else if (localName.equals("section")) {
				ISectioningNode node = this.mySection.newSectioningNode(localName);
				
				this.myContainer = node;
				this.mySection = node;
			} else if (localName.equals("title")) {
				IContainerNode node = this.mySection.newTitle(localName);

				this.myContainer = node;
			} else if (localName.equals("p")) {
				IContainerNode node = this.myContainer.newContainerNode(localName);

				this.myContainer = node;
				
				this.myParseText  = true;
			} else if (localName.equals("empty-line")
					|| localName.equals("strong")
					|| localName.equals("emphasis")
					|| localName.equals("strikethrough")
					|| localName.equals("sub")
					|| localName.equals("sup")
					|| localName.equals("code")
							) {
				IContainerNode node = this.myContainer.newContainerNode(localName);

				this.myContainer = node;
			} else {
				throw new SAXParseException("Unsupported element: " + localName, this.myLocator);
			}


/*			{
				String id;
				if ((id = attributes.getValue("id")) != null) {
					this.myBook.setNodeID(id, node);
				}
			}*/

		}
		
		/**
		 * An utitlity function. It generates a string from {@link java.lang.StringBuilder} with
		 * starting and leading whitespace chars removed.
		 * @param builder the <code>StringBuilder</code> to generate string
		 * @param trimEnd 
		 * @param trimStart 
		 * @return a string from <code>builder</code>.
		 */
		private static String trimStringBuilder(StringBuilder builder, boolean trimStart, boolean trimEnd) {
			int length = builder.length();
			if ((length == 0)
					|| (builder.charAt(0) > '\u0020')
					&& (builder.charAt(length-1) > '\u0020')) {
				return builder.toString();
			}

			int begin = 0;
			int end = length-1;
			if (trimStart) {
				while ((begin <= end) && (builder.charAt(begin) <= '\u0020')) {
					begin++;
				}
			}
			if (trimEnd) {
				while ((begin <= end) && (builder.charAt(end) <= '\u0020')) {
					end--;
				}
			}
			if (begin > end)
				return "";
			return builder.substring(begin, end+1);
		}
		
	}

}
