package org.jbookreader.ui.swing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

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
		
		this.myDialog.getContentPane().setLayout(new BorderLayout());
		
		this.myDialog.add(createContents());
		this.myDialog.add(createButtonPane(), BorderLayout.SOUTH);

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

	private JComponent createContents() {
		JPanel pane = new JPanel();
		JPanel option;
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		
		option = new JPanel();
		option.setLayout(new BoxLayout(option, BoxLayout.X_AXIS));
		option.add(this.myAntialias = new JCheckBox("Antialias text"));
		pane.add(option);
		
		option = new JPanel();
		option.setLayout(new BoxLayout(option, BoxLayout.X_AXIS));
		option.add(new JLabel("Font Family"));
		option.add(this.myFontFamily = new JTextField(15));
		pane.add(option);
		
		option = new JPanel();
		option.setLayout(new BoxLayout(option, BoxLayout.X_AXIS));
		option.add(new JLabel("Font Size"));
		option.add(this.myFontSize = new JSpinner());
		pane.add(option);

		return pane;
	}
	
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

	private JComponent createButtonPane() {
		JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));

		pane.add(new JButton(new OKAction()));
		pane.add(new JButton(new CancelAction()));

		return pane;
	}

}
