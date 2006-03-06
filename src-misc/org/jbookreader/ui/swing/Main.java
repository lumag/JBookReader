package org.jbookreader.ui.swing;

import javax.swing.*;

public class Main {
	public static void createAndShowGUI() {
        	//Make sure we have nice window decorations.
		JFrame.setDefaultLookAndFeelDecorated(true);

		/* DemoApplication app = */ new DemoApplication();
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
