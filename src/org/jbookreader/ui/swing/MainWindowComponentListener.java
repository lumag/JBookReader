/**
 * 
 */
package org.jbookreader.ui.swing;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;

class MainWindowComponentListener extends ComponentAdapter {
	@Override
	public void componentMoved(ComponentEvent e) {
		Point point = MainWindow.getMainWindow().getFrame().getLocation();
		Config.getConfig().setIntValue("main_x", point.x);
		Config.getConfig().setIntValue("main_y", point.y);
		try {
			Config.getConfig().save();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	@Override
	public void componentResized(ComponentEvent e) {
		Dimension dim = MainWindow.getMainWindow().getFrame().getSize();
		Config.getConfig().setIntValue("main_width", dim.width);
		Config.getConfig().setIntValue("main_height", dim.height);
		try {
			Config.getConfig().save();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}