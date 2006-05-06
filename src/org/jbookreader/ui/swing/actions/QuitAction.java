package org.jbookreader.ui.swing.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.jbookreader.ui.swing.Config;
import org.jbookreader.ui.swing.MainWindow;
import org.jbookreader.ui.swing.Messages;


/**
 * This class represents an application quit action.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 * 
 */
@SuppressWarnings("serial")
public class QuitAction extends AbstractAction {
	
	private QuitAction() {
		putValue(NAME, Messages.getString("QuitAction.Name")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, Integer.valueOf(Messages.getString("QuitAction.Mnemonic").charAt(0))); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("QuitAction.Description")); //$NON-NLS-1$
	}

	private static Action ourAction;

	/**
	 * Returns the instance of this action type.
	 * @return the instance of this action type.
	 */
	public static Action getAction() {
		if (ourAction == null) {
			ourAction = new QuitAction();
		}

		return ourAction;
	}

	public void actionPerformed(ActionEvent e) {
		String posref = MainWindow.getMainWindow().getBookComponent().getDisplayNodeReference();
		if (posref != null) {
			Config.getConfig().setStringValue("book_position", posref);
			try {
				Config.getConfig().save();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		MainWindow.getMainWindow().getFrame().dispose();
	}

}
