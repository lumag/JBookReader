package org.jbookreader.ui.swing;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

public class PageDownAction extends AbstractAction {
	
	PageDownAction() {
		putValue(NAME, Messages.getString("PageDownAction.Name")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, Messages.getString("PageDownAction.Mnemonic")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("PageDownAction.Description")); //$NON-NLS-1$
	}

	static Action action;

	static Action getAction() {
		if (action == null) {
			action = new PageDownAction();
		}

		return action;
	}

	public void actionPerformed(ActionEvent e) {
		MainWindow.getMainWindow().getBookComponent().scrollPageDown();
	}

}
