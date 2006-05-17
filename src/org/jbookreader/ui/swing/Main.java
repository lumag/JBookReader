/*
 * JBookReader - Java FictionBook Reader
 * Copyright (C) 2006 Dmitry Baryshkov
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *   
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *   
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the Free Software
 *   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
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
		
		main.getBookComponent().setAntialias(Config.getConfig().getBooleanValue("antialias"));
		main.getBookComponent().setDefaultFont(
				Config.getConfig().getStringValue("fontfamily"),
				Config.getConfig().getIntValue("fontsize"));
		
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
				} else {
					String url = Config.getConfig().getStringValue("book_url");
					String position = Config.getConfig().getStringValue("book_position");
					if (url != null) {
						MainWindow.getMainWindow().openBook(new File(url));
					}
					
					if (position != null && !position.equals("")) {
						MainWindow.getMainWindow().getBookComponent().setBookPositionByReference(position);
					}
				}
			}
		});
	}
}
