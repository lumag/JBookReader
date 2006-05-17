package org.jbookreader.ui.swing.actions;

import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.jbookreader.ui.swing.MainWindow;
import org.jbookreader.ui.swing.Messages;

/**
 * This class represents a about action.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 * 
 */
@SuppressWarnings("serial")
public class AboutAction extends AbstractAction {
	
	private AboutAction() {
		putValue(NAME, Messages.getString("AboutAction.Name")); //$NON-NLS-1$
		putValue(MNEMONIC_KEY, Integer.valueOf(Messages.getString("AboutAction.Mnemonic").charAt(0))); //$NON-NLS-1$
		putValue(SHORT_DESCRIPTION, Messages.getString("AboutAction.Description")); //$NON-NLS-1$
		
	}

	private static Action ourAction;

	/**
	 * Returns the instance of this action type.
	 * @return the instance of this action type.
	 */
	public static Action getAction() {
		if (ourAction == null) {
			ourAction = new AboutAction();
		}

		return ourAction;
	}

	public void actionPerformed(ActionEvent e) {
		JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

		JLabel label = new JLabel("JBookReader " + org.jbookreader.Values.version, SwingConstants.CENTER);
		label.setFont(new Font("Sans", Font.BOLD, 16));
//		label.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		pane.add(label);

		pane.add(new JLabel("(C) 2006 Dmitry Baryshkov (dbaryshkov@gmail.com)"));
		pane.add(new JLabel(""));
		pane.add(new JLabel("JBookReader comes with ABSOLUTELY NO WARRANTY."));
		pane.add(new JLabel("This is free software, and you are welcome"));
		pane.add(new JLabel("to redistribute it under conditions of GPL."));
					

		JOptionPane.showMessageDialog(MainWindow.getMainWindow().getFrame(),
				pane,
				"About...",
				JOptionPane.INFORMATION_MESSAGE);
	}

}
