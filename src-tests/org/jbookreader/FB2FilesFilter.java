package org.jbookreader;

import java.io.File;
import java.io.FilenameFilter;

/**
 * This is simple file filter that accepts only .fb2 and .xml files.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 */
public class FB2FilesFilter implements FilenameFilter {
	public boolean accept(File dir, String name) {
		if (name.endsWith(".fb2"))
			return true;
		if (name.endsWith(".xml"))
			return true;
		return false;
	}
	
}