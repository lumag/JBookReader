package org.jbookreader.ui.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.jbookreader.ui.swing.MainWindow;
import org.jbookreader.ui.swing.Messages;

@SuppressWarnings("serial") //$NON-NLS-1$
public class ScrollUpAction extends AbstractAction {
	
	private ScrollUpAction() {
		putValue(NAME, Messages.getString("ScrollUpAction.Name")); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("ScrollUpAction.Description")); //$NON-NLS-1$
	}

	private static Action ourAction;

	public static Action getAction() {
		if (ourAction == null) {
			ourAction = new ScrollUpAction();
		}

		return ourAction;
	}

	public void actionPerformed(ActionEvent e) {
		// XXX: change to system property
		MainWindow.getMainWindow().getBookComponent().scrollUp(1);
	}

}
