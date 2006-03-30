package org.jbookreader.ui.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.jbookreader.ui.swing.MainWindow;
import org.jbookreader.ui.swing.Messages;

@SuppressWarnings("serial")
public class PageUpAction extends AbstractAction {
	
	private PageUpAction() {
		putValue(NAME, Messages.getString("PageUpAction.Name")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, Messages.getString("PageUpAction.Mnemonic")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("PageUpAction.Description")); //$NON-NLS-1$
	}

	private static Action ourAction;

	public static Action getAction() {
		if (ourAction == null) {
			ourAction = new PageUpAction();
		}

		return ourAction;
	}

	public void actionPerformed(ActionEvent e) {
		MainWindow.getMainWindow().getBookComponent().scrollPageUp();
	}

}
