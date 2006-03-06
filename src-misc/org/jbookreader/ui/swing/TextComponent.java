package org.jbookreader.ui.swing;

import javax.swing.*;
import java.awt.*;

public class TextComponent extends JComponent {
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int w = getWidth();
		int h = getHeight();
		int leftW = w / 2;
		int topH = h / 2;

//		// Paint the top left and bottom right in red.
//		g.setColor(Color.RED);
//		g.fillRect(0, 0, leftW, topH);
//		g.fillRect(leftW, topH, w - leftW, h - topH);
//
//		// Paint the bottom left and top right in white.
//		g.setColor(Color.WHITE);
//		g.fillRect(0, topH, leftW, h - topH);
//		g.fillRect(leftW, 0, w - leftW, topH);

		Graphics2D g2d = (Graphics2D)g.create(); // copy g

		g2d.drawRect(0, 0, 10, 10);

		g2d.setFont(new Font("Serif", Font.BOLD|Font.PLAIN, 10));
		g2d.drawString("works", 20, 20);
	}

	
}
