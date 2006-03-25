package org.jbookreader.ui.swing;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;

import org.jbookreader.book.parser.FB2Parser;


public class OpenAction extends AbstractAction {
	
	private OpenAction() {
		putValue(NAME, Messages.getString("OpenAction.Name")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, Integer.valueOf(Messages.getString("OpenAction.Mnemonic").charAt(0))); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("OpenAction.Description")); //$NON-NLS-1$
	}

	static Action action;

	static Action getAction() {
		if (action == null) {
			action = new OpenAction();
		}

		return action;
	}

	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		GenericFileFilter filter = new GenericFileFilter();
		filter.addExtension("fb2"); //$NON-NLS-1$
		filter.setDescription(Messages.getString("FB2Description")); //$NON-NLS-1$
		chooser.setFileFilter(filter);
		
		int returnValue = chooser.showOpenDialog(MainWindow.getMainWindow().getFrame());
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			MainWindow.getMainWindow().openBook(chooser.getSelectedFile());
		}
	}

}
