package org.jbookreader.ui.swing;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JDialog;

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
		if (Config.getConfig().getBooleanValue("windows_decorated")) {
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
		}

		MainWindow main = MainWindow.getMainWindow();
		main.getFrame().setVisible(true);
	}

	/**
	 * Main function of the application. But it just delegates control to the Swing.
	 * @param args currently unused.
	 */
	public static void main(String[] args) {
		final String bookname = args.length>0?args[0]:null;

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
				if (bookname != null) {
					MainWindow.getMainWindow().openBook(new File(bookname));
				}
			}
		});
	}
}
