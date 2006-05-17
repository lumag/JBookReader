package org.jbookreader.ui.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.jbookreader.ui.swing.Messages;
import org.jbookreader.ui.swing.OptionsDialog;

/**
 * This class represents a preferences action action.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 * 
 */
@SuppressWarnings("serial")
public class PreferencesAction extends AbstractAction {
	
	private PreferencesAction() {
		putValue(NAME, Messages.getString("PreferencesAction.Name")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, Integer.valueOf(Messages.getString("PreferencesAction.Mnemonic").charAt(0))); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("PreferencesAction.Description")); //$NON-NLS-1$
		
	}

	private static Action ourAction;

	/**
	 * Returns the instance of this action type.
	 * @return the instance of this action type.
	 */
	public static Action getAction() {
		if (ourAction == null) {
			ourAction = new PreferencesAction();
		}

		return ourAction;
	}

	public void actionPerformed(ActionEvent e) {
		OptionsDialog.getOptionsDialog().getDialog().setVisible(true);
	}

}
