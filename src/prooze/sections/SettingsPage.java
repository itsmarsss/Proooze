package prooze.sections;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import prooze.ColorPalette;
import prooze.Proze;
import prooze.components.ProzeBarUI;
import prooze.components.ProzeButton;
import prooze.components.ProzeNotification;
import prooze.components.ProzePanel;

public class SettingsPage extends ProzePanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	JScrollPane settingsScrollPane;
	JLabel blackListedProcessNames;
	JTextArea blackListTextArea;
	JLabel breakTimeLabel;
	JSlider breakTimerSlider;
	ProzeButton startTimer;
	JLabel countDown;
	boolean counting = false;
	public SettingsPage(Color background) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		super(background);

		int gap = Proze.getGap();
		setLayout(null);

		JPanel settingsPanel = new JPanel();
		settingsPanel.setBackground(ColorPalette.palette.getBackgroundColor2());
		settingsPanel.setLayout(new GridBagLayout());
		settingsScrollPane = new JScrollPane(settingsPanel);
		settingsScrollPane.getVerticalScrollBar().setUI(new ProzeBarUI(ColorPalette.palette.getBackgroundColor(), ColorPalette.palette.getBackgroundColor().darker(), true));
		settingsScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(Proze.getGap()/2, 0));
		settingsScrollPane.getVerticalScrollBar().setUnitIncrement(20);
		settingsScrollPane.setBorder(null);
		settingsScrollPane.setOpaque(false);

		add(settingsScrollPane);

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;

		blackListedProcessNames = new JLabel("Process Names to NOT track (separated and ended by \",\" and \",,\" to clear)");
		blackListedProcessNames.setFont(new Font("Sans Serif", Font.PLAIN, 30));
		blackListedProcessNames.setSize(700, 35);

		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 0, 0);
		settingsPanel.add(blackListedProcessNames, gbc);

		blackListTextArea = new JTextArea(",,");
		blackListTextArea.setFont(new Font("Sans Serif", Font.PLAIN, 30));
		blackListTextArea.setPreferredSize(new Dimension(700, 210));
		blackListTextArea.setLineWrap(true);
		blackListTextArea.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == ',') {
					Proze.clear();
					String[]black = blackListTextArea.getText().split(",");
					for(String s:black) {
						Proze.addBlackList(s.trim());
					}
					for(String s:Proze.getBlackList())
						System.out.println(s);
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {}

		});
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 0, gap, 0);
		settingsPanel.add(blackListTextArea, gbc);

		breakTimeLabel = new JLabel();
		breakTimeLabel.setFont(new Font("Sans Serif", Font.PLAIN, 30));
		breakTimeLabel.setSize(600, 35);

		gbc.gridy = 2;
		gbc.insets = new Insets(0, 0, 0, 0);
		settingsPanel.add(breakTimeLabel, gbc);

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		breakTimerSlider = new JSlider(1, 720, 15);
		breakTimerSlider.setSnapToTicks(true);
		breakTimerSlider.setMinorTickSpacing(15);
		breakTimerSlider.setMajorTickSpacing(30);
		breakTimerSlider.setPaintLabels(true);
		breakTimerSlider.setPaintTicks(true);
		breakTimerSlider.setBackground(null);

		gbc.gridy = 3;
		gbc.insets = new Insets(0, 0, gap/2, 0);
		settingsPanel.add(breakTimerSlider, gbc);

		breakTimeLabel.setText("Break Time Reminder | " + breakTimerSlider.getValue() + " minute(s)");

		breakTimerSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				breakTimeLabel.setText("Break Time Reminder | " + breakTimerSlider.getValue() + " minute(s)");
			}

		});

		startTimer = new ProzeButton(ColorPalette.palette.getMainColor(), "Start Timer");
		Font font = new Font("Sans Serif", Font.PLAIN, 30);
		startTimer.setFont(font);
		startTimer.setPreferredSize(new Dimension(160, 40));
		startTimer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				counting = !counting;
				if(counting) {								
					countDown = new JLabel();
					countDown.setFont(font);
					gbc.gridy = 5;
					settingsPanel.add(countDown, gbc);
					ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
					ses.scheduleAtFixedRate(new Runnable() {
						private long time = breakTimerSlider.getValue()*60;
						private long tmptime = breakTimerSlider.getValue()*60;
						@Override
						public void run() {
							if(!counting) {
								SettingsPage.notification("Uhoh, timer has been terminated", "You've terminated your break time timer");
								settingsPanel.remove(countDown);
								settingsPanel.revalidate();
								settingsPanel.repaint();
								startTimer.setText("Start Timer");
								ses.shutdown();
							}else {
								startTimer.setText("End Timer");
								countDown.setText(tmptime + " second(s) left...");
								if(tmptime == 0) {
									SettingsPage.notification("Get Back to Work!", "You've passed your " + time/60 + " minute free time!!!");
									settingsPanel.remove(countDown);
									settingsPanel.revalidate();
									settingsPanel.repaint();
									startTimer.setText("Start Timer");
									ses.shutdown();
								}
								tmptime--;
							}
						}
					}, 0, 1, TimeUnit.SECONDS);
				}
			}

		});

		gbc.gridy = 4;
		settingsPanel.add(startTimer, gbc);

		setSize();
	}

	protected static void notification(String a, String b) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Agree");
				ProzeNotification pn = new ProzeNotification();
				try {
					pn.displayTray(a, b);
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).run();
	}

	public void setSize() {
		int gap = Proze.getGap();
		setSize(Proze.getPanel().getWidth()-(gap*2), Proze.getPanel().getHeight()-Proze.getSelectionButtons().getHeight()-(gap*3));
		setLocation(gap, Proze.getSelectionButtons().getHeight());
		settingsScrollPane.setSize(getSize());
		breakTimerSlider.setPreferredSize(new Dimension((int)(getWidth()/1.5), 40));

		revalidate();
		repaint();
	}

	public ProzeButton getStartTimer() {
		return startTimer;
	}
}
