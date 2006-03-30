package org.jbookreader.ui.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import org.jbookreader.book.bom.IBook;
import org.jbookreader.book.parser.FB2Parser;
import org.jbookreader.ui.swing.actions.OpenAction;
import org.jbookreader.ui.swing.actions.PageDownAction;
import org.jbookreader.ui.swing.actions.PageUpAction;
import org.jbookreader.ui.swing.actions.QuitAction;
import org.jbookreader.ui.swing.actions.ScrollDownAction;
import org.jbookreader.ui.swing.actions.ScrollUpAction;
import org.xml.sax.SAXException;

/**
 * This class represents a main application window.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
@SuppressWarnings("serial") //$NON-NLS-1$
public class MainWindow {
	/**
	 * This is the heart of the application &mdash; the book component
	 */
	private JBookComponent myBookComponent;
	/**
	 * The frame for this window.
	 */
	private JFrame myFrame;
	/**
	 * This creates main window contents.
	 * @return a JComponent to be placed on the ContentsPlane.
	 */
	private JComponent createContents() {
		JPanel pane = new JPanel(new BorderLayout());

		this.myBookComponent = new JBookComponent();
		
		this.myBookComponent.setOpaque(true);
//		Dimension dim = new Dimension(600,400);
		pane.add(this.myBookComponent, BorderLayout.CENTER);	
//		pane.setMinimumSize(dim);
//		pane.setPreferredSize(dim);

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
		
		menu = new JMenu(Messages.getString("File")); //$NON-NLS-1$
		menu.setMnemonic(KeyEvent.VK_F);
		
		menuItem = new JMenuItem(OpenAction.getAction());
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		
		menu.add(new JSeparator());
		
		menuItem = new JMenuItem(QuitAction.getAction());
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
		
		menu = new JMenu(Messages.getString("Edit")); //$NON-NLS-1$
		menu.setMnemonic(KeyEvent.VK_E);
		
		menuItem = new JMenuItem(Messages.getString("Find"), KeyEvent.VK_F); //$NON-NLS-1$
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		
		menuItem = new JMenuItem(Messages.getString("FindNext"), KeyEvent.VK_X); //$NON-NLS-1$
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		
		menuItem = new JMenuItem(Messages.getString("FindPrevious"), KeyEvent.VK_V); //$NON-NLS-1$
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
		menu.add(menuItem);
		
		menu.add(new JSeparator());

		menuItem = new JMenuItem(Messages.getString("Preferences"), KeyEvent.VK_E); //$NON-NLS-1$
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
		
		menu = new JMenu(Messages.getString("View")); //$NON-NLS-1$
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
		
		menu = new JMenu(Messages.getString("Bookmarks")); //$NON-NLS-1$
		menu.setMnemonic(KeyEvent.VK_B);
		
		menuItem = new JMenuItem(Messages.getString("AddBookmark"), KeyEvent.VK_A); //$NON-NLS-1$
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		
		menuItem = new JMenuItem(Messages.getString("EditBookmarks"), KeyEvent.VK_E); //$NON-NLS-1$
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
		
		menu = new JMenu(Messages.getString("Help")); //$NON-NLS-1$
		menu.setMnemonic(KeyEvent.VK_H);

		menuItem = new JMenuItem(Messages.getString("About"), KeyEvent.VK_A); //$NON-NLS-1$
		menu.add(menuItem);
		
		return menu;
	}

	/**
	 * Constructs main application window by placing on the JFrame
	 * contents, menu and packing it.
	 *
	 */
	public MainWindow() {
		this.myFrame = new JFrame();
		this.myFrame.setTitle("JBookReader"); //$NON-NLS-1$
		this.myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.myFrame.setJMenuBar(createMenuBar());

		Container contentPane = this.myFrame.getContentPane();

		JComponent component = createContents();
		component.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		contentPane.add(component, BorderLayout.CENTER);
		
		this.myFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, 0),
					PageDownAction.getAction().getValue(Action.NAME));
		this.myFrame.getRootPane().getActionMap().put(
				PageDownAction.getAction().getValue(Action.NAME),
				PageDownAction.getAction());

		this.myFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, 0),
					PageUpAction.getAction().getValue(Action.NAME));
		this.myFrame.getRootPane().getActionMap().put(
				PageUpAction.getAction().getValue(Action.NAME),
				PageUpAction.getAction());

		this.myFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0),
					ScrollDownAction.getAction().getValue(Action.NAME));
		this.myFrame.getRootPane().getActionMap().put(
				ScrollDownAction.getAction().getValue(Action.NAME),
				ScrollDownAction.getAction());

		this.myFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0),
					ScrollUpAction.getAction().getValue(Action.NAME));
		this.myFrame.getRootPane().getActionMap().put(
				ScrollUpAction.getAction().getValue(Action.NAME),
				ScrollUpAction.getAction());

		Dimension dim = new Dimension(640,480);
		this.myFrame.setPreferredSize(dim);
		this.myFrame.pack();
	}

	/**
	 * The instance of the main window.
	 */
	private static MainWindow ourWindow;
	/**
	 * Returns the main window object. If the window wasn't created, creates new
	 *  instance.
	 * @return the main window object.
	 */
	public static MainWindow getMainWindow() {
		if (ourWindow == null) {
			ourWindow = new MainWindow();
		}
		return ourWindow;
	}

	/**
	 * Opens book at specified file.
	 * @param file the file of the book
	 */
	public void openBook(File file) {
		try {
			IBook book;
			if (file.getName().endsWith(".zip")) { // $NON-NLS-1$
				ZipInputStream stream = new ZipInputStream(new FileInputStream(file));
				stream.getNextEntry();
				book = FB2Parser.parse(stream);
			} else {
				book = FB2Parser.parse(file);
			}
			this.myBookComponent.setBook(book);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Returns the <code>JFrame</code> representing the main window.
	 * @return the <code>JFrame</code> representing the main window.
	 */
	public JFrame getFrame() {
		return this.myFrame;
	}

	/**
	 * Disposes the frame and all associated resources.
	 *
	 */
	public void dispose() {
		this.myFrame.dispose();
	}

	/**
	 * Returns current book component.
	 * @return current book component.
	 */
	public JBookComponent getBookComponent() {
		return this.myBookComponent;
	}
}
