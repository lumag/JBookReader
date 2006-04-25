package org.jbookreader.ui.swing.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;

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
	
	private OpenAction() {
		putValue(NAME, Messages.getString("OpenAction.Name")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, Integer.valueOf(Messages.getString("OpenAction.Mnemonic").charAt(0))); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("OpenAction.Description")); //$NON-NLS-1$
		
		this.myLastDirectory = new File(System.getProperty("user.dir"));
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

	private File myLastDirectory = null;

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

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			MainWindow.getMainWindow().openBook(chooser.getSelectedFile());
		}
	}

}
