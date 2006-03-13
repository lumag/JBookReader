package org.jbookreader.ui.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DemoApplication {
	private JComponent createComponent() {
		JPanel pane = new JPanel();

		JComponent text = new TextComponent();
		Dimension dim = new Dimension(200,100);
		text.setMinimumSize(dim);
		text.setPreferredSize(dim);
		pane.add(text);	

		return pane;
//		return text;
	}
	public DemoApplication() {
		JFrame frame = new JFrame("Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container contentPane = frame.getContentPane();
		JComponent component = createComponent();
		component.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 12));

		contentPane.add(component, BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);
	}
}
