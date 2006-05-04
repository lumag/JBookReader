package org.jbookreader.ui.swing.config;

import java.util.EventListener;

/**
 * This class represents a value changed event listener.
 *
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 * 
 * @see ValueEvent
 *
 * @param <T> the type of the value.
 */
public interface ValueListener<T> extends EventListener {
	/**
	 * Invoked when the value was changed.
	 * @param event the corresponding event object
	 */
	void valueChanged(ValueEvent<T> event);
}
