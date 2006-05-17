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
/**
 * 
 */
package org.jbookreader.ui.swing;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;

import org.jbookreader.ui.swing.actions.QuitAction;

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
	
	@Override
	public void componentHidden(ComponentEvent e) {
		QuitAction.getAction().actionPerformed(new ActionEvent(e.getSource(), e.getID(), null));
	}
}