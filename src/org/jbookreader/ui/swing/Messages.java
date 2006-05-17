/*
 * JBookReader - Java FictionBook Reader
 * Copyright (C) 2006 Dmitry Baryshkov
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *   
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *   
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the Free Software
 *   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
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
