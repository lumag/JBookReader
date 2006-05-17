package org.jbookreader.ui.swing;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class OptionsDialog {
	private class OptionsComponentListener extends ComponentAdapter {
		@Override
		public void componentShown(ComponentEvent e) {
			fillValues();
		}
	}
	
	@SuppressWarnings("serial")
	private class OKAction extends AbstractAction {
		public OKAction() {
			putValue(NAME, "OK");
			putValue(MNEMONIC_KEY, Integer.valueOf('O'));
		}
		public void actionPerformed(ActionEvent e) {
			OptionsDialog.this.commitValues();
		}
		
	}

	@SuppressWarnings("serial")
	private class CancelAction extends AbstractAction {
		public CancelAction() {
			putValue(NAME, "Cancel");
			putValue(MNEMONIC_KEY, Integer.valueOf('C'));
		}
		public void actionPerformed(ActionEvent e) {
			OptionsDialog.this.getDialog().setVisible(false);
		}
	}

	private static OptionsDialog ourOptionsDialog;
	private final JDialog myDialog;
	
	private OptionsDialog() {
		this.myDialog = new JDialog(MainWindow.getMainWindow().getFrame());
		this.myDialog.setTitle("Options");
		this.myDialog.setModal(true);

		this.myDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		
		this.myDialog.addComponentListener(new OptionsComponentListener());
		
		Container pane = this.myDialog.getContentPane();

		pane.setLayout(new GridLayout(4, 2));

		pane.add(new JLabel());

		pane.add(this.myAntialias = new JCheckBox("Antialias text"));

		pane.add(new JLabel("Font Family", SwingConstants.CENTER));
		pane.add(this.myFontFamily = new JTextField(15));

		pane.add(new JLabel("Font Size", SwingConstants.CENTER));
		pane.add(this.myFontSize = new JSpinner());

		pane.add(new JButton(new OKAction()));
		pane.add(new JButton(new CancelAction()));

		this.myDialog.pack();
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
	
	private JCheckBox myAntialias;
	private JTextField myFontFamily;
	private JSpinner myFontSize;

	private void fillValues() {
		this.myAntialias.setSelected(Config.getConfig().getBooleanValue("antialias"));
		this.myFontFamily.setText(Config.getConfig().getStringValue("fontfamily"));
		this.myFontSize.setValue(Config.getConfig().getIntValue("fontsize"));
	}

	public void commitValues() {
		boolean antialias;
		String family;
		int size;

		Config.getConfig().setBooleanValue("antialias", antialias = this.myAntialias.isSelected());
		Config.getConfig().setStringValue("fontfamily", family = this.myFontFamily.getText());
		Config.getConfig().setIntValue("fontsize", size = (Integer)this.myFontSize.getValue());

		MainWindow.getMainWindow().getBookComponent().setAntialias(antialias);
		MainWindow.getMainWindow().getBookComponent().setDefaultFont(family, size);
		MainWindow.getMainWindow().getBookComponent().repaint();
		
		try {
			Config.getConfig().save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		OptionsDialog.this.getDialog().setVisible(false);
	}

}
