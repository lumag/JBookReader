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