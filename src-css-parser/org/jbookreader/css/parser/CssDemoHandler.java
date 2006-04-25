package org.jbookreader.css.parser;

import org.w3c.css.sac.*;
import static org.w3c.css.sac.LexicalUnit.*;
import static org.w3c.css.sac.Selector.*;
import static org.w3c.css.sac.Condition.*;

/**
 * A demonstration handler for CSS documents.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class CssDemoHandler implements DocumentHandler {
	public void startDocument(InputSource source) throws CSSException {
//		System.err.println("$called startDocument");
	}

	public void endDocument(InputSource source) throws CSSException {
//		System.err.println("$called endDocument");
	}

	public void comment(String text) throws CSSException {
		System.err.println("$called comment");
	}

	public void ignorableAtRule(String atRule) throws CSSException {
		System.err.println("$called ignorableAtRule");
	}

	public void namespaceDeclaration(String prefix, String uri) throws CSSException {
		System.err.println("$called namespaceDeclaration");
	}

	public void importStyle(String uri, SACMediaList media, String defaultNamespaceURI) throws CSSException {
		System.err.println("$called importStyle");
	}

	public void startMedia(SACMediaList media) throws CSSException {
		System.err.println("$called startMedia");
	}

	public void endMedia(SACMediaList media) throws CSSException {
		System.err.println("$called endMedia");
	}

	public void startPage(String name, String pseudo_page) throws CSSException {
		System.err.println("$called startPage");
	}

	public void endPage(String name, String pseudo_page) throws CSSException {
		System.err.println("$called endPage");
	}

	public void startFontFace() throws CSSException {
		System.err.println("$called startFontFace");
	}

	public void endFontFace() throws CSSException {
		System.err.println("$called endFontFace");
	}

	private String formatCondition(Condition condition) {
		switch (condition.getConditionType()) {
			case SAC_AND_CONDITION: // 0
				{
					CombinatorCondition cond = (CombinatorCondition) condition;
					return	formatCondition(cond.getFirstCondition()) +
						formatCondition(cond.getSecondCondition());
				}
			case SAC_ATTRIBUTE_CONDITION: //4
				{
					AttributeCondition cond = (AttributeCondition) condition;

					String text = "[" + cond.getLocalName();
					if (cond.getSpecified()) {
						text += "=\"" + cond.getValue() + "\"";
					}
					return text + "]";
				}
			case SAC_ID_CONDITION: // 5
				{
					AttributeCondition cond = (AttributeCondition) condition;
					return "#" + cond.getValue();
				}
			case SAC_LANG_CONDITION: // 6
				{
					LangCondition cond = (LangCondition) condition;
					return ":lang(" + cond.getLang() + ")";
				}
			case SAC_ONE_OF_ATTRIBUTE_CONDITION: // 7
				{
					AttributeCondition cond = (AttributeCondition) condition;
					return "[" + cond.getLocalName() + "~=\"" + cond.getValue() + "\"]";
				}
			case SAC_BEGIN_HYPHEN_ATTRIBUTE_CONDITION: // 8
				{
					AttributeCondition cond = (AttributeCondition) condition;
					return "[" + cond.getLocalName() + "|=\"" + cond.getValue() + "\"]";
				}
			case SAC_CLASS_CONDITION: // 9
				{
					AttributeCondition cond = (AttributeCondition) condition;
					return "." + cond.getValue();
				}
			case SAC_PSEUDO_CLASS_CONDITION: // 10
				{
					AttributeCondition cond = (AttributeCondition) condition;
					return ":" + cond.getValue();
				}
			default:
				{
					return "$condition_" + Integer.toString(condition.getConditionType());
				}
		}
	}

	private String formatSelector(Selector selector, boolean toplevel) {
		switch (selector.getSelectorType()) {
			case SAC_CONDITIONAL_SELECTOR: // 0
				{
					ConditionalSelector sel = (ConditionalSelector) selector;
					return	formatSelector(sel.getSimpleSelector(), false) + 
						formatCondition(sel.getCondition());
				}
			case SAC_ELEMENT_NODE_SELECTOR: // 4
				{
					ElementSelector sel = (ElementSelector) selector;
					// XXX: CSS3:namespaces. Not supported by known parsers.
//					return sel.getNamespaceURI() + '|' + sel.getLocalName();
					String localName = sel.getLocalName();
					if (localName == null) {
						return toplevel?"*":"";
					}
					return localName;
				}
			
			case SAC_PSEUDO_ELEMENT_SELECTOR: // 9
				{
					ElementSelector sel = (ElementSelector) selector;
					// XXX: CSS3:namespaces. Not supported by known parsers.
					return ":" + sel.getLocalName();
				}

			case SAC_DESCENDANT_SELECTOR: // 10
				{
					DescendantSelector sel = (DescendantSelector) selector;
					return	formatSelector(sel.getAncestorSelector(), true) +
						" " +
						formatSelector(sel.getSimpleSelector(), true);
				}

			case SAC_CHILD_SELECTOR: // 11
				{
					DescendantSelector sel = (DescendantSelector) selector;
					Selector ancestor = sel.getAncestorSelector();

			// hack to w/a flute's parsing of 'p:first-line' as '* > :first-line'
					if (ancestor.getSelectorType() == SAC_ELEMENT_NODE_SELECTOR
							&& ((ElementSelector) ancestor).getLocalName() == null
							&& sel.getSimpleSelector().getSelectorType() == SAC_PSEUDO_ELEMENT_SELECTOR) {
						return formatSelector(sel.getSimpleSelector(), true);
					}
					return	formatSelector(ancestor, true) +
						" > " +
						formatSelector(sel.getSimpleSelector(), true);
				}

			case SAC_DIRECT_ADJACENT_SELECTOR: // 12
				{
					SiblingSelector sel = (SiblingSelector) selector;
					return	formatSelector(sel.getSelector(), true) +
						" + " +
						formatSelector(sel.getSiblingSelector(), true);
				}

			default:
				{
					return "$selector_" + Integer.toString(selector.getSelectorType());
				}
		}
	}

	public void startSelector(SelectorList selectors) throws CSSException {
		// System.err.println("$called startSelector");
		int len = selectors.getLength();
		for (int i = 0; i < len; i ++) {
			if (i != 0) {
				System.out.print(", ");
			}

			Selector sel = selectors.item(i);
			System.out.print(formatSelector(sel, true));
//			System.out.print(sel.toString());
//			System.out.print(sel.getClass().toString());
		}

		System.out.println(" {");
	}

	public void endSelector(SelectorList selectors) throws CSSException {
//		System.err.println("$called endSelector");
		System.out.println("}");
		System.out.println();
	}

	public void property(String name, LexicalUnit value, boolean important) throws CSSException {
//		System.err.println("$called property");
//		System.out.format("\t%s: %s%s;%n", name, value.toString(), important?" !important":"");

		System.out.format("\t%s:", name);
		while (value != null) {
			String text;
			switch (value.getLexicalUnitType()) {
			case SAC_OPERATOR_COMMA:
			    text = ",";
			    break;
			case SAC_OPERATOR_PLUS:
			    text = "+";
			    break;
			case SAC_OPERATOR_MINUS:
			    text = "-";
			    break;
			case SAC_OPERATOR_MULTIPLY:
			    text = "*";
			    break;
			case SAC_OPERATOR_SLASH:
			    text = "/";
			    break;
			case SAC_OPERATOR_MOD:
			    text = "%";
			    break;
			case SAC_OPERATOR_EXP:
			    text = "^";
			    break;
			case SAC_OPERATOR_LT:
			    text = "<";
			    break;
			case SAC_OPERATOR_GT:
			    text = ">";
			    break;
			case SAC_OPERATOR_LE:
			    text = "<=";
			    break;
			case SAC_OPERATOR_GE:
			    text = "=>";
			    break;
			case SAC_OPERATOR_TILDE:
			    text = "~";
			    break;
			case SAC_INHERIT:
			    text = "inherit";
			    break;
			case SAC_INTEGER:
			    text = Integer.toString(value.getIntegerValue(), 10);
			    break;
			case SAC_REAL:
			    text = String.valueOf(value.getFloatValue());
			    break;
			case SAC_EM:
			case SAC_EX:
			case SAC_PIXEL:
			case SAC_INCH:
			case SAC_CENTIMETER:
			case SAC_MILLIMETER:
			case SAC_POINT:
			case SAC_PICA:
			case SAC_PERCENTAGE:
			case SAC_DEGREE:
			case SAC_GRADIAN:
			case SAC_RADIAN:
			case SAC_MILLISECOND:
			case SAC_SECOND:
			case SAC_HERTZ:
			case SAC_KILOHERTZ:
			case SAC_DIMENSION:
			    float f = value.getFloatValue();
			    int i = (int) f;
			    if (i == f) {
				text = i + value.getDimensionUnitText();
			    } else {
				text = f + value.getDimensionUnitText();
			    }
			    break;
			case SAC_URI:
			    text = "uri(" + value.getStringValue() + ")";
			    break;
			    // FIXME: rgbcolor, attributes (attr(...))
			case SAC_COUNTER_FUNCTION:
			case SAC_COUNTERS_FUNCTION:
			case SAC_RGBCOLOR:
			case SAC_RECT_FUNCTION:
			case SAC_FUNCTION:
			    text = value.getFunctionName() + "(" + value.getParameters() + ")";
			    break;
			case SAC_IDENT:
			    text = value.getStringValue();
			    break;
			case SAC_STRING_VALUE:
			    // FIXME. not exact
			    text = "\"" + value.getStringValue() + "\"";
			    break;
			case SAC_ATTR:
			    // FIXME
			    text = "attr(" + value.getStringValue() + ")";
			    break;
			case SAC_UNICODERANGE:
			    text = "@@TODO";
			    break;
			case SAC_SUB_EXPRESSION:
			    text = value.getSubValues().toString();
			    break;
			default:
			    text = "@unknown";
			    break;
			}

			System.out.format(" %s", text);
			value = value.getNextLexicalUnit();
		}
		
		if (important) {
			System.out.print(" !important");
		}

		System.out.println(";");
	}
	
}
