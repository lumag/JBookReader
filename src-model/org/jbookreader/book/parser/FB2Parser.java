package org.jbookreader.book.parser;

import java.io.IOException;

import org.jbookreader.book.BinaryData;
import org.jbookreader.book.Book;
import org.jbookreader.book.bom.AbstractContainerNode;
import org.jbookreader.book.bom.BodyNode;
import org.jbookreader.book.bom.IContainerNode;
import org.jbookreader.book.bom.ISectioningNode;
import org.jbookreader.book.bom.SectioningNode;
import org.jbookreader.book.bom._TextNode;
import org.jbookreader.book.bom.internal.ContainerNode;
import org.jbookreader.book.stylesheet.EDisplayType;
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
	public static Book parse(String uri) throws IOException, SAXException {
		XMLReader reader;
		
		reader = XMLReaderFactory.createXMLReader();
		reader.setErrorHandler(new ParseErrorHandler());

		Book book = new Book();
		
		reader.setContentHandler(new FB2ContentsHandler(book));

		reader.parse(new InputSource(uri));

		return book;	
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
		private final Book myBook;
		/**
		 * Binary blob data being parsed.
		 */
		private BinaryData myBinaryData;

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
		 * FIXME: remove this!!!!!
		 * it a workaround: we can't parseXML metadata currently. Only bodies.
		 */
		private boolean parseXML = false;
		
		/**
		 * This constructs new parser for given <code>book</code>
		 * @param book the book being parsed.
		 */
		public FB2ContentsHandler(Book book) {
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
			
			this.myText.append(ch, start, length);
		}
		
		/**
		 * This processes information in <code>this.myText</code> into new #text node.
		 *
		 *@see FB2ContentsHandler#myText
		 */
		private void processTextNode() {
			String string = trimStringBuilder(this.myText);
			this.myText.setLength(0);
			
			if (string.length() == 0) {
				return;
			}
			_TextNode node = new _TextNode();
			node.setText(string);

			this.myContainer.addChildNode(node);

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
				processTextNode();
				
				this.myContainer = this.myContainer.getParentNode();

				if (this.myContainer == null || this.myContainer.isSectioningNode()) {
					this.mySection = (ISectioningNode)(this.myContainer);
				}
			}

//			System.out.println("/" + localName);
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
			
			processTextNode();
			
			if (localName.equals("FictionBook")) {
				// XXX: root book node
			} else if (localName.equals("binary")) {
				this.myBinaryData = new BinaryData();
				this.myBinaryData.setContentType(attributes.getValue("content-type"));
				this.myBinaryData.setID(attributes.getValue("id"));
				this.myBook.addBinaryData(this.myBinaryData);
			} else if (localName.equals("body")) {
				BodyNode node = new BodyNode();

				handleBody(node, localName, attributes);
			} else if (localName.equals("section")) {
				SectioningNode node = new SectioningNode();
				
				handleSectioning(node, localName, attributes);
			} else if (localName.equals("title")) {
				ContainerNode node = new ContainerNode();
				node.setDisplayType(EDisplayType.BLOCK);

				handleTitle(node, localName, attributes);
			} else if (localName.equals("p") || localName.equals("empty-line")) {
				ContainerNode node = new ContainerNode();
				node.setDisplayType(EDisplayType.BLOCK);

				// FIXME handleParagraph
				handleContainer(node, localName, attributes);
			} else if(	   localName.equals("strong")
					|| localName.equals("emphasis")
					|| localName.equals("strikethrough")
					|| localName.equals("sub")
					|| localName.equals("sup")
					|| localName.equals("code")
							) {
				ContainerNode node = new ContainerNode();
				node.setDisplayType(EDisplayType.INLINE);

				handleContainer(node, localName, attributes);
			} else {
				throw new SAXParseException("Unsupported element: " + localName, this.myLocator);
			}

//			System.out.println(localName);
		}
		
		/**
		 * Processes the title node.
		 * 
		 * @param node the node to process
		 * @param localName the XML tag name
		 * @param attributes XML attributes
		 */
		private void handleTitle(ContainerNode node, String localName, Attributes attributes) {
			handleContainer(node, localName, attributes);
			this.mySection.setTitle(node);
		}

		/**
		 * Processes the body node.
		 * 
		 * @param node the node to process
		 * @param localName the XML tag name
		 * @param attributes XML attributes
		 */
		private void handleBody(BodyNode node, String localName, Attributes attributes) {
			handleSectioning(node, localName, attributes);

			this.myBook.addBody(node);
		}

		/**
		 * Processes the sectioning node.
		 * 
		 * @param node the node to process
		 * @param localName the XML tag name
		 * @param attributes XML attributes
		 */
		private void handleSectioning(SectioningNode node, String localName, Attributes attributes) {
			handleContainer(node, localName, attributes);

			this.mySection = node;
		}

		/**
		 * Processes abstract container node.
		 * 
		 * @param node the node to process
		 * @param localName the XML tag name
		 * @param attributes XML attributes
		 */
		private void handleContainer(AbstractContainerNode node, String localName, Attributes attributes) {
			handleNode(node, localName, attributes);

			this.myContainer = node;
		}

		/**
		 * Processes abstract node.
		 * 
		 * @param node the node to process
		 * @param localName the XML tag name
		 * @param attributes XML attributes
		 */
		private void handleNode(AbstractContainerNode node, String localName, Attributes attributes) {
			node.setTagName(localName);
			
			String id;
			
			if ((id = attributes.getValue("id")) != null) {
				this.myBook.setNodeID(id, node);
			}
			
			if (this.myContainer != null) {
				this.myContainer.addChildNode(node);
			}
		}

		/**
		 * An utitlity function. It generates a string from {@link java.lang.StringBuilder} with
		 * starting and leading whitespace chars removed.
		 * @param builder the <code>StringBuilder</code> to generate string
		 * @return a string from <code>builder</code>.
		 */
		private static String trimStringBuilder(StringBuilder builder) {
			int length = builder.length();
			if ((length == 0)
					|| (builder.charAt(0) > '\u0020')
					&& (builder.charAt(length-1) > '\u0020')) {
				return builder.toString();
			}

			int begin = 0;
			int end = length-1;
			while ((begin <= end) && (builder.charAt(begin) <= '\u0020')) {
				begin++;
			}
			while ((begin <= end) && (builder.charAt(end) <= '\u0020')) {
				end--;
			}
			if (begin > end)
				return "";
			return builder.substring(begin, end+1);
		}
		
	}

}
