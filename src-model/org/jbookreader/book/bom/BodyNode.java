package org.jbookreader.book.bom;

/**
 * This class represents a &lt;body&gt; node: the possibly-named root section. 
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class BodyNode extends SectioningNode {
	/**
	 * The name of the body.
	 */
	private String myName;

	/**
	 * Returns the name of the body or null if body is unnamed.
	 * @return the name of the body.
	 */
	public String getName() {
		return this.myName;
	}

	/**
	 * Sets the name of the body.
	 * @param name new name
	 */
	public void setName(String name) {
		this.myName = name;
	} 
}
