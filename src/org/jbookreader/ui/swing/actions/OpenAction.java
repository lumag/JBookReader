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
package org.jbookreader.ui.swing.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;

import org.jbookreader.ui.swing.Config;
import org.jbookreader.ui.swing.GenericFileFilter;
import org.jbookreader.ui.swing.MainWindow;
import org.jbookreader.ui.swing.Messages;

/**
 * This class represents an open file action.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 * 
 */
@SuppressWarnings("serial")
public class OpenAction extends AbstractAction {
	
	private File myLastDirectory = null;

	private OpenAction() {
		putValue(NAME, Messages.getString("OpenAction.Name")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, Integer.valueOf(Messages.getString("OpenAction.Mnemonic").charAt(0))); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("OpenAction.Description")); //$NON-NLS-1$
		
		String dir = Config.getConfig().getStringValue("lastdir");
		
		if (dir == null) {
			dir = System.getProperty("user.dir");
		}
		this.myLastDirectory = new File(dir);
	}

	private static Action ourAction;

	/**
	 * Returns the instance of this action type.
	 * @return the instance of this action type.
	 */
	public static Action getAction() {
		if (ourAction == null) {
			ourAction = new OpenAction();
		}

		return ourAction;
	}

	public void actionPerformed(ActionEvent e) {

		JFileChooser chooser = new JFileChooser();

		GenericFileFilter filter = new GenericFileFilter();
		filter.addExtension("fb2"); //$NON-NLS-1$
		filter.addExtension("fb2.zip"); //$NON-NLS-1$
		filter.setDescription(Messages.getString("FB2Description")); //$NON-NLS-1$
		chooser.setFileFilter(filter);

		chooser.setCurrentDirectory(this.myLastDirectory);
		
		int returnValue = chooser.showOpenDialog(MainWindow.getMainWindow().getFrame());

		this.myLastDirectory = chooser.getCurrentDirectory();
		Config.getConfig().setStringValue("lastdir", this.myLastDirectory.getAbsolutePath());
		try {
			Config.getConfig().save();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			MainWindow.getMainWindow().openBook(chooser.getSelectedFile());
		}
	}

}
