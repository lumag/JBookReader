package org.jbookreader.ui.swing.config;

import java.util.EventObject;

/**
 * This class represents a value changed event.
 *
 * @param <T> the type of the value
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 */
@SuppressWarnings("serial")
public class ValueEvent<T> extends EventObject {

	/**
	 * New value.
	 */
	private T myValue;

	/**
	 * This constructs new value changed event.
	 * @param source the source of the change.
	 * @param value new value
	 */
	public ValueEvent(Object source, T value) {
		super(source);
		this.myValue = value;
	}
	
	/**
	 * Returns new value.
	 * @return new value.
	 */
	public T getValue() {
		return this.myValue;
	}

}
