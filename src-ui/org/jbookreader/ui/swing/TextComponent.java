package org.jbookreader.ui.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JComponent;

public class TextComponent extends JComponent {
	protected void paintComponent(Graphics g) {
		if (isOpaque()) { // paint background
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
		}

		Graphics g2d = g.create(); // copy g
		Insets insets = getInsets();
		g2d.translate(insets.left, insets.top);

		int w = getWidth() - insets.left - insets.right;
		int h = getHeight() - insets.top - insets.bottom;
//		System.out.println(w + "x" + h);

		g2d.setClip(0, 0, w, h);

		g2d.drawRect(0, 0, 10, 10);

		g2d.setColor(Color.BLACK);
		g2d.drawRect(0, 0, w-1, h-1);

		g2d.setFont(new Font("Serif", Font.BOLD|Font.PLAIN, 10));
		g2d.drawString("works", 20, 20);

		g2d.dispose();

	}

}
