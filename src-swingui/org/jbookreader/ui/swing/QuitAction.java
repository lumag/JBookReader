package org.jbookreader.ui.swing;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;


public class QuitAction extends AbstractAction {
	
	private QuitAction() {
		putValue(NAME, Messages.getString("QuitAction.Name")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, Integer.valueOf(Messages.getString("QuitAction.Mnemonic").charAt(0))); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("QuitAction.Description")); //$NON-NLS-1$
	}

	static Action action;

	static Action getAction() {
		if (action == null) {
			action = new QuitAction();
		}

		return action;
	}

	public void actionPerformed(ActionEvent e) {
		MainWindow.getMainWindow().dispose();
	}

}
