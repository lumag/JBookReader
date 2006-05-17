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

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import org.jbookreader.ui.swing.actions.PageDownAction;
import org.jbookreader.ui.swing.actions.PageUpAction;
import org.jbookreader.ui.swing.actions.ScrollDownAction;
import org.jbookreader.ui.swing.actions.ScrollUpAction;

class MainWindowMouseWheelListener implements MouseWheelListener {
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getScrollType() == MouseWheelEvent.WHEEL_BLOCK_SCROLL) {
//			System.out.println("block scroll " + e.getWheelRotation());
			if (e.getWheelRotation() > 0) {
				PageUpAction.getAction().actionPerformed(null); // FIXME: provide cleaner way to do this!;
			} else {
				PageDownAction.getAction().actionPerformed(null); // FIXME: provide cleaner way to do this!;
			}
		} else {
			int units = e.getUnitsToScroll();
//			System.out.println("unit scroll " + units);
			if (units > 0) {
				while (units > 0) {
					ScrollDownAction.getAction().actionPerformed(null); // FIXME: provide cleaner way to do this!;
					units --;
				}
			} else {
				while (units < 0) {
					ScrollUpAction.getAction().actionPerformed(null); // FIXME: provide cleaner way to do this!;
					units ++;
				}
			}
		}
	}
}