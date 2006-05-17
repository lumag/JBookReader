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

import org.jbookreader.util.Configuration;

/**
 * This is the configuration for the SwingUI JBR.
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class Config extends Configuration {

	private static String DEFAULT_RESOURCE = "org/jbookreader/ui/swing/DefaultValues.properties";

	private static Configuration ourConfig;
	
	private Config() {
		super("JBookReader", "swingui", DEFAULT_RESOURCE);
	}

	/**
	 * Returns the configuration instance.
	 * @return the configuration instance.
	 */
	public static Configuration getConfig() {
		if (ourConfig == null) {
			ourConfig = new Config();
		}

		return ourConfig;
	}
}
