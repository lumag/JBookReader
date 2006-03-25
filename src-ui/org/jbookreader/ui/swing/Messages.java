package org.jbookreader.ui.swing;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * This is localization <code>ResourceBundle</code> wrapper.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class Messages {
	/**
	 * The name of the properties file
	 */
	private static final String BUNDLE_NAME = "org.jbookreader.ui.swing.messages"; //$NON-NLS-1$

	/**
	 * The loaded resourse bundle
	 */
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	/**
	 * Private constructor to disallow instantiation, etc.
	 *
	 */
	private Messages() {
		/**
		 * Empty.
		 */
	}

	/**
	 * Returns the message associated with the  specified key.
	 * @param key the key for the message
	 * @return the message for the 
	 */
	public static String getString(String key) {
		// TODO Auto-generated method stub
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
