package org.jbookreader.ui.swing;

import javax.swing.JFrame;

/**
 * This is a main class for the Swing UI.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class Main {
	/**
	 * Create a nice GUI. It's executed in the SwingEventThread
	 *
	 */
	public static void createAndShowGUI() {
        		//Make sure we have nice window decorations.
		JFrame.setDefaultLookAndFeelDecorated(true);

		JFrame main = new MainWindow();
		main.setVisible(true);
	}

	/**
	 * Main function of the application. But it just delegates control to the Swing.
	 * @param args currently unused.
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
