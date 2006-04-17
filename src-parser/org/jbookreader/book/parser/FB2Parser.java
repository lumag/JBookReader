package org.jbookreader.book.parser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.jbookreader.book.bom.IBinaryData;
import org.jbookreader.book.bom.IBook;
import org.jbookreader.book.bom.IContainerNode;
import org.jbookreader.book.bom.IImageNode;
import org.jbookreader.book.bom.INode;
import org.jbookreader.book.bom.impl.Book;
import org.jbookreader.book.stylesheet.IStyleSheet;
import org.jbookreader.book.stylesheet.fb2.FB2StyleSheet;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
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
		return parse(new InputSource(uri));
	}

	/**
	 * This parses the book at provided <code>InputStream</code>.
	 * @param stream the stream with the book
	 * @return the parsed book representation
	 * @throws IOException in case of I/O problem
	 * @throws SAXException in case of XML parsing problem
	 */
	public static IBook parse(InputStream stream) throws IOException, SAXException {
		return parse(new InputSource(stream));
	}

	/**
	 * This parses the book at provided <code>InputSource</code>.
	 * @param source the source with the book
	 * @return the parsed book representation
	 * @throws IOException in case of I/O problem
	 * @throws SAXException in case of XML parsing problem
	 */
	private static IBook parse(InputSource source) throws IOException, SAXException {
		XMLReader reader;
		
		reader = XMLReaderFactory.createXMLReader();
		reader.setErrorHandler(new ParseErrorHandler());

		IBook book = new Book();
		book.setSystemStyleSheet(getFB2StyleSheet());
		
		reader.setContentHandler(new FB2ContentsHandler(book));

		reader.parse(source);

		return book;	
	}
	
	/**
	 * Parses specified book
	 * @param file the file with the book
	 * @return the parsed book representation
	 * @throws IOException in case of I/O problem
	 * @throws SAXException in case of XML parsing problem
	 */
	public static IBook parse(File file) throws IOException, SAXException {
		return parse(file.getAbsolutePath());
	}
	
	// FIXME: after finishing stylesheet loading, replace with reading of stylesheet.
	/**
	 * The fb2 format stylesheet
	 */
	private static IStyleSheet ssheet;
	/**
	 * Returns FB2 system style sheet.
	 * @return FB2 system style sheet.
	 */
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
		@SuppressWarnings("unused")
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

		/**
		 * True if the current node can containt text.
		 */
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
		public void characters(char[] ch, int start, int length) {
			if (!this.parseXML) {
				return;
			}
			
			if (!this.myParseText)
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
			if (!this.myParseText)
				return;
			
			String string = trimStringBuilder(this.myText, this.hadOpenTag, hasCloseTag);
//			String string = this.myText.toString();
			this.myText.setLength(0);
			
			if (string.length() == 0) {
				return;
			}
			
			this.myContainer.newTextNode(string);

//			System.out.println("#text: '" + string + "'");
		}
		
		/**
		 * Returns true if passed tag name denotes start of paragraph.
		 * @param tagName the name of the tag
		 * @return whether passed tag name denotes start of paragraph.
		 */
		private boolean isParagraphTag(String tagName) {
			if (tagName.equals("p")
			 || tagName.equals("subtitle")
			 || tagName.equals("text-author")
			 || tagName.equals("v"))
				return true;
			
			return false;
		}

		@Override
		public void endElement(String uri, String localName, String qName) {
			if (!this.parseXML) {
				return;
			}
			
			// System.out.println("</" + localName + ":" + ((this.myContainer!=null)?this.myContainer.getTagName():"null"));
			if (localName.equals("FictionBook")) {
				// XXX: root node;
			} else if (localName.equals("binary")) {
				this.myBinaryData.setBase64Encoded(this.myText.toString().toCharArray());
				this.myText.setLength(0);
				this.myBinaryData = null;
				this.myParseText = false;
			} else {
				// part of body
				processTextNode(true);
				this.hadOpenTag = false;
				
				if (isParagraphTag(localName)) {
					this.myParseText = false;
				}
				
				if (this.myContainer.getTagName().equals(localName)) {
					this.myContainer = this.myContainer.getParentNode();
				}

			}

			// System.out.println("</" + localName);
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) {
			if (!this.parseXML) {
				if (!localName.equals("body")) {
					return;
				}
//				System.out.println("Got first body tag");
				
				this.parseXML = true;
			}
			
			// System.out.println("<" + localName);

			if (localName.equals("FictionBook")) {
				// XXX: root book node
			} else if (localName.equals("binary")) {
				this.myBinaryData = this.myBook.newBinaryData(attributes.getValue("id"), attributes.getValue("content-type"));
				this.myParseText = true;
				return;
			} else {
				INode node = null;

				processTextNode(false);
				this.hadOpenTag = true;
			
				if (localName.equals("body")) {
					node = this.myBook.newBody("body", attributes.getValue("name"));
				} else if (localName.equals("title")) {
					node = this.myContainer.newTitle(localName);
				} else if (localName.equals("image")) {
					String href = null;
					href = attributes.getValue("http://www.w3.org/1999/xlink", "href");
					if (href == null) {
						href = attributes.getValue("href");
					}
					if (href == null) {
						System.err.println("Can'f find image href!");
					}
					IImageNode image = this.myContainer.newImage(localName, href);
					String str;
					if ((str = attributes.getValue("alt")) != null) {
						image.setText(str);
					}
					if ((str = attributes.getValue("title")) != null) {
						image.setTitle(str);
					}
					node = image;
				} else if (isParagraphTag(localName)) {
					node = this.myContainer.newContainerNode(localName);
	
					this.myParseText  = true;
				} else {
					/*
					 * Threat every unknown node as a simple container.
					 */
					node = this.myContainer.newContainerNode(localName);
				}
				
				String classAttribute;
				
				if (localName.equals("p")) {
					classAttribute = "style";
				} else if (localName.equals("style")) {
					classAttribute = "name";
				} else {
					classAttribute = "class";
				}
				node.setNodeClass(attributes.getValue(classAttribute));
				
				if (node.getContainer() != null) {
					this.myContainer = node.getContainer();
				}

				{
					String id;
					if ((id = attributes.getValue("id")) != null) {
						node.setID(id);
					}
				}
			}



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
			if (begin > end) {
				return "";
			}
			return builder.substring(begin, end+1);
		}
		
	}

}
