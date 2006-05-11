package org.jbookreader.ui.swing;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OptionsDialog {
	private static OptionsDialog ourOptionsDialog;
	private final JDialog myDialog;
	
	private OptionsDialog() {
		this.myDialog = new JDialog(MainWindow.getMainWindow().getFrame());
		this.myDialog.setTitle("Options");
		
		this.myDialog.add(createContents());

		this.myDialog.pack();
		this.myDialog.setVisible(true);
	}
	
	public static OptionsDialog getOptionsDialog() {
		if (ourOptionsDialog == null) {
			ourOptionsDialog = new OptionsDialog();
		}
		return ourOptionsDialog;
	}
	
	public JDialog getDialog() {
		return this.myDialog;
	}

	private JComponent createContents() {
		JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		
		JPanel option = new JPanel();
		option.setLayout(new BoxLayout(option, BoxLayout.Y_AXIS));
		option.add(new JLabel("Antialias"));
		pane.add(option);
		return pane;
	}
}
