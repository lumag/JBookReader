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

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.swing.filechooser.FileFilter;

/**
 * This class represents generic file filter for Swing dialogs
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class GenericFileFilter extends FileFilter {
	/**
	 * The description of the filter
	 */
	private String myDescription;
	
	/**
	 * The set of possible extensions.
	 */
	private Set<String> myExtension = new HashSet<String>();

	@Override
	public boolean accept(File pathname) {
		if (pathname.isDirectory()) {
			return true;
		}
		String name = pathname.getName();
		int dotPosition = name.length();
		while ((dotPosition = name.lastIndexOf(".", dotPosition-1)) > 0) { //$NON-NLS-1$
			if (this.myExtension.contains(name.substring(dotPosition+1))) {
				return true;
			}
		}
		if (this.myExtension.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		return this.myDescription;
	}

	/**
	 * Sets the description of the filter
	 * @param description
	 */
	public void setDescription(String description) {
		this.myDescription = description;
	}

	/**
	 * Adds the extension to the list of possible extensions.
	 * @param extension the extension of the file (without starting dot).
	 */
	public void addExtension(String extension) {
		this.myExtension.add(extension);
	}
}
