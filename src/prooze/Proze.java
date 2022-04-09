package prooze;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;

import prooze.processes.ProcessInfo;
import prooze.processes.UpdateProcesses;
import prooze.sections.CurrentStatusPage;
import prooze.sections.SelectionButtons;
import prooze.sections.SettingsPage;
import prooze.sections.SummaryPage;

public class Proze {
	public static void main(String[] args) throws URISyntaxException {
		AppPath = new File(ClassLoader.getSystemClassLoader().getResource(".").toURI()).getPath();
		System.setProperty("sun.java2d.uiScale", "1");
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new Proze();
				} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static String AppPath;
	public static String getAppPath() {
		return AppPath;
	}
	
	private static LinkedList<String> blackList = new LinkedList<String>();
	public static void addBlackList(String black) {
		blackList.add(black);
	}	
	public static LinkedList<String> getBlackList() {
		return blackList;
	}
	public static void clear() {
		blackList.clear();
	}
	
	private static LinkedList<ProcessInfo> currentRunning = new LinkedList<ProcessInfo>();
	public static LinkedList<ProcessInfo> getCurrentRunning() {
		return currentRunning;
	}
	
	private final static int gap = 40;
	
	private static JFrame mainFrame;
	private static JPanel viewPanel;
	TrayIcon trayIcon = null;
	SystemTray tray = null;
	Proze() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		mainFrame = new JFrame("Proooze");
		mainFrame.setIconImage(ResourceLoader.loader.getIcon().getImage());

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.setSize(new Dimension((int)(screenSize.getWidth()/2), (int)(screenSize.getHeight()/2)));
		mainFrame.setMinimumSize(new Dimension((int)(screenSize.getWidth()/2), (int)(screenSize.getHeight()/2)));
		mainFrame.setLayout(null);
		mainFrame.validate();

		viewPanel = new JPanel();
		viewPanel.setLayout(null);
		viewPanel.setBackground(ColorPalette.palette.getBackgroundColor());

		setSize();

		mainFrame.add(viewPanel);
		mainFrame.setVisible(true);
		initComponents();
		mainFrame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent componentEvent) {
				reSize();
			}
		});
		
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		if(SystemTray.isSupported()){
			tray = SystemTray.getSystemTray();

			Image image = ResourceLoader.loader.getIcon().getImage();

			PopupMenu popup = new PopupMenu();
			MenuItem open = new MenuItem("Open");
			open.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainFrame.setVisible(true);
					mainFrame.setExtendedState(JFrame.NORMAL);
					tray.remove(trayIcon);
				}
			});
			popup.add(open);
			
			MenuItem exit = new MenuItem("Exit");
			exit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tray.remove(trayIcon);
					System.exit(0);
				}
			});
			popup.add(exit);

			popup.addSeparator();
			
			MenuItem summary = new MenuItem("Summary");
			summary.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainFrame.setVisible(true);
					mainFrame.setExtendedState(JFrame.NORMAL);
					tray.remove(trayIcon);
					buttons.getSummaryButton().doClick();
				}
			});
			popup.add(summary);
			
			MenuItem status = new MenuItem("Status");
			status.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainFrame.setVisible(true);
					mainFrame.setExtendedState(JFrame.NORMAL);
					tray.remove(trayIcon);
					buttons.getCurrentButton().doClick();
				}
			});
			popup.add(status);
			
			MenuItem settings = new MenuItem("Settings");
			settings.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainFrame.setVisible(true);
					mainFrame.setExtendedState(JFrame.NORMAL);
					tray.remove(trayIcon);
					buttons.getSettingsButton().doClick();
				}
			});
			popup.add(settings);
			
			trayIcon = new TrayIcon(image, "Proooze", popup);
			trayIcon.setImageAutoSize(true);
			trayIcon.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainFrame.setVisible(true);
					mainFrame.setExtendedState(JFrame.NORMAL);
					tray.remove(trayIcon);
				}
			});
		}
		mainFrame.addWindowListener(new WindowListener() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					tray.add(trayIcon);
					mainFrame.setVisible(false);
				} catch (AWTException ex) {}
			}

			@Override
			public void windowOpened(WindowEvent e) {}

			@Override
			public void windowClosed(WindowEvent e) {
				try {
					tray.add(trayIcon);
					mainFrame.setVisible(false);
				} catch (AWTException ex) {}
			}

			@Override
			public void windowIconified(WindowEvent e) {}

			@Override
			public void windowDeiconified(WindowEvent e) {}

			@Override
			public void windowActivated(WindowEvent e) {}

			@Override
			public void windowDeactivated(WindowEvent e) {}
		});
		ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
		ses.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					UpdateProcesses.update();
				} catch (IOException | AWTException e) {
					// Popup
					e.printStackTrace();
				}

				Proze.getFrame().repaint();
				Proze.getFrame().revalidate();
			}
		}, 0, 3, TimeUnit.SECONDS);
	}
	private void setSize() {
		viewPanel.setSize(mainFrame.getWidth(), mainFrame.getHeight());
	}
	private void reSize() {
		setSize();
		buttons.setSize();
		summaryPage.setSize();
		currentPage.setSize();
		settingsPage.setSize();
	}
	private static SelectionButtons buttons;
	private static SummaryPage summaryPage;
	private static CurrentStatusPage currentPage;
	private static SettingsPage settingsPage;
	private void initComponents() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		buttons = new SelectionButtons();
		summaryPage = new SummaryPage(ColorPalette.palette.getBackgroundColor2());
		currentPage = new CurrentStatusPage(ColorPalette.palette.getBackgroundColor2());
		settingsPage = new SettingsPage(ColorPalette.palette.getBackgroundColor2());

		viewPanel.add(buttons);
		viewPanel.add(summaryPage);
		viewPanel.add(currentPage);
		viewPanel.add(settingsPage);
		summaryPage.setVisible(false);
		settingsPage.setVisible(false);

	}
	public static JFrame getFrame() {
		return mainFrame;
	}
	public static JPanel getPanel() {
		return viewPanel;
	}
	public static int getGap() {
		return gap;
	}

	public static SelectionButtons getSelectionButtons() {
		return buttons;
	}
	public static SummaryPage getSummaryPage() {
		return summaryPage;
	}
	public static CurrentStatusPage getCurrentPage() {
		return currentPage;
	}
	public static SettingsPage getSettingsPage() {
		return settingsPage;
	}
}
