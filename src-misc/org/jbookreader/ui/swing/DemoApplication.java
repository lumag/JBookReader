package org.jbookreader.ui.swing;

import javax.swing.*;
import java.awt.*;

public class DemoApplication {
	private JComponent createComponent() {
//		JPanel pane = new JPanel();

		JComponent text = new TextComponent();
		Dimension dim = new Dimension(100,100);
		text.setMinimumSize(dim);
		text.setPreferredSize(dim);
//		pane.add(text);	

		return text;
//		return pane;
	}
	public DemoApplication() {
		JFrame frame = new JFrame("Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container contentPane = frame.getContentPane();
		JComponent component = createComponent();
		component.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

		contentPane.add(component, BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);
	}
}
