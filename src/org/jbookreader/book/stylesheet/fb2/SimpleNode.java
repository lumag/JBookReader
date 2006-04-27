package org.jbookreader.book.stylesheet.fb2;

class SimpleNode {
	final String name;
	final String klass;
	final String id;

	SimpleNode(final String name, final String klass, final String id) {
		this.name = name;
		this.klass = klass;
		this.id = id;
	}
	
	@Override
	public String toString() {
		return this.name + '.' + this.klass + '#' + this.id; 
	}
}
