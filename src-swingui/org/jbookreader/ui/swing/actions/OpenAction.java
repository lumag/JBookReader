package org.jbookreader.ui.swing.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;

import org.jbookreader.ui.swing.GenericFileFilter;
import org.jbookreader.ui.swing.MainWindow;
import org.jbookreader.ui.swing.Messages;

@SuppressWarnings("serial")
public class OpenAction extends AbstractAction {
	
	private OpenAction() {
		putValue(NAME, Messages.getString("OpenAction.Name")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, Integer.valueOf(Messages.getString("OpenAction.Mnemonic").charAt(0))); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("OpenAction.Description")); //$NON-NLS-1$
	}

	private static Action ourAction;

	public static Action getAction() {
		if (ourAction == null) {
			ourAction = new OpenAction();
		}

		return ourAction;
	}

	private File lastDirectory = null;

	public void actionPerformed(ActionEvent e) {

		JFileChooser chooser = new JFileChooser();

		GenericFileFilter filter = new GenericFileFilter();
		filter.addExtension("fb2"); //$NON-NLS-1$
		filter.addExtension("fb2.zip"); //$NON-NLS-1$
		filter.setDescription(Messages.getString("FB2Description")); //$NON-NLS-1$
		chooser.setFileFilter(filter);

		chooser.setCurrentDirectory(lastDirectory);
		
		int returnValue = chooser.showOpenDialog(MainWindow.getMainWindow().getFrame());

		lastDirectory = chooser.getCurrentDirectory();

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			MainWindow.getMainWindow().openBook(chooser.getSelectedFile());
		}
	}

}
