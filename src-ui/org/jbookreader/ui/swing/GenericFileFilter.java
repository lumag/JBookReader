package org.jbookreader.ui.swing;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.swing.filechooser.FileFilter;

public class GenericFileFilter extends FileFilter {
	private String myDescription;
	
	private Set<String> myExtension = new HashSet<String>();

	@Override
	public boolean accept(File pathname) {
		if (pathname.isDirectory())
			return true;
		String name = pathname.getName();
		String ext = name.substring(name.lastIndexOf(".") + 1); //$NON-NLS-1$
		return this.myExtension.contains(ext);
	}

	@Override
	public String getDescription() {
		return this.myDescription;
	}

	public void setDescription(String description) {
		this.myDescription = description;
	}

	public void addExtension(String extension) {
		this.myExtension.add(extension);
	}
}
