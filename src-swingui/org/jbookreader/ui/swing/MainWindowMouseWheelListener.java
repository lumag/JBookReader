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