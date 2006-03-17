package org.jbookreader.ui.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

/**
 * This class represents a main application window.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	/**
	 * This creates main window contents.
	 * @return a JComponent to be placed on the ContentsPlane.
	 */
	private JComponent createContents() {
		JPanel pane = new JPanel(new BorderLayout());

		JComponent text = new TextComponent();
		
		text.setOpaque(true);
		Dimension dim = new Dimension(200,100);
		pane.add(text, BorderLayout.CENTER);	
		pane.setMinimumSize(dim);
		pane.setPreferredSize(dim);

		return pane;
	}

	/**
	 * Creates main menu bar.
	 * @return main menu bar.
	 */
	private JMenuBar createMenuBar() {
		JMenuBar mainmenu  = new JMenuBar();
		
		mainmenu.add(createFileMenu());
		mainmenu.add(createEditMenu());
		mainmenu.add(createViewMenu());
		mainmenu.add(createBookmarksMenu());
		mainmenu.add(createHelpMenu());
		
		return mainmenu;
	}

	/**
	 * Creates file menu.
	 * @return file menu.
	 */
	private JMenu createFileMenu() {
		JMenu menu;
		JMenuItem menuItem;
		
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		
		menuItem = new JMenuItem("Open", KeyEvent.VK_O);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		
		menu.add(new JSeparator());
		
		menuItem = new JMenuItem("Quit", KeyEvent.VK_Q);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		
		return menu;
	}

	/**
	 * Creates edit menu.
	 * @return edit menu.
	 */
	private JMenu createEditMenu() {
		JMenu menu;
		JMenuItem menuItem;
		
		menu = new JMenu("Edit");
		menu.setMnemonic(KeyEvent.VK_E);
		
		menuItem = new JMenuItem("Find", KeyEvent.VK_F);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Find Next", KeyEvent.VK_X);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Find Previous", KeyEvent.VK_V);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
		menu.add(menuItem);
		
		menu.add(new JSeparator());

		menuItem = new JMenuItem("Preferences", KeyEvent.VK_E);
		menu.add(menuItem);
		
		return menu;
	}

	/**
	 * Creates view menu.
	 * @return view menu.
	 */
	private JMenu createViewMenu() {
		JMenu menu;
//		JMenuItem menuItem;
		
		menu = new JMenu("View");
		menu.setMnemonic(KeyEvent.VK_V);
		
		// FIXME: add some generic view actions
		
		return menu;
	}

	/**
	 * Creates bookmarks menu.
	 * @return bookmarks menu.
	 */
	private JMenu createBookmarksMenu() {
		JMenu menu;
		JMenuItem menuItem;
		
		menu = new JMenu("Bookmarks");
		menu.setMnemonic(KeyEvent.VK_B);
		
		menuItem = new JMenuItem("Add Bookmark", KeyEvent.VK_A);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Edit Bookmarks", KeyEvent.VK_E);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		
		menu.add(new JSeparator());
		
		// FIXME: handle bookmarks list externally!
		
		return menu;
	}

	/**
	 * Creates help menu
	 * @return help menu.
	 */
	private JMenu createHelpMenu() {
		JMenu menu;
		JMenuItem menuItem;
		
		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_H);

		menuItem = new JMenuItem("About", KeyEvent.VK_A);
		menu.add(menuItem);
		
		return menu;
	}

	/**
	 * Constructs main application window by placing on the JFrame
	 * contents, menu and packing it.
	 *
	 */
	public MainWindow() {
		setTitle("JBookReader");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setJMenuBar(createMenuBar());

		Container contentPane = getContentPane();

		JComponent component = createContents();
		component.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		contentPane.add(component, BorderLayout.CENTER);

		pack();
	}

}
