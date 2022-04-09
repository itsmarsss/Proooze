package prooze.sections;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import prooze.ColorPalette;
import prooze.Proze;
import prooze.components.ProzeBarUI;
import prooze.components.ProzePanel;

public class CurrentStatusPage extends ProzePanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static JLabel currentStatusLabel;
	private static JPanel processesPanel;
	private static JScrollPane processesScrollPane;
	public CurrentStatusPage(Color background) {
		super(background);

		setLayout(null);

		currentStatusLabel = new JLabel("      Current Status:");
		currentStatusLabel.setFont(new Font("Sans Serif", Font.BOLD, 30));
		add(currentStatusLabel);

		processesPanel = new JPanel();
		processesScrollPane = new JScrollPane(processesPanel);
		processesScrollPane.getVerticalScrollBar().setUI(new ProzeBarUI(ColorPalette.palette.getBackgroundColor(), ColorPalette.palette.getBackgroundColor().darker(), false));
		processesScrollPane.getVerticalScrollBar().setUnitIncrement(20);
		processesScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(Proze.getGap()/2, 0));
		processesScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
		processesScrollPane.setBorder(null);
		processesPanel.setOpaque(true);
		processesPanel.setLayout(new GridBagLayout());
		add(processesScrollPane);

		setSize();
		setColor();

		currentStatusLabel.repaint();
		currentStatusLabel.revalidate();

		processesPanel.repaint();
		processesPanel.revalidate();

		processesScrollPane.repaint();
		processesScrollPane.revalidate();

		repaint();
		revalidate();

		Proze.getFrame().repaint();
		Proze.getFrame().revalidate();
	}
	private void setColor() {
		processesPanel.setBackground(new Color(255, 255, 255));
		setBackground(ColorPalette.palette.getBackgroundColor2());
	}
	public void setSize() {
		int gap = Proze.getGap();
		setSize(Proze.getPanel().getWidth()-(gap*2), Proze.getPanel().getHeight()-Proze.getSelectionButtons().getHeight()-(gap*3));
		setLocation(gap, Proze.getSelectionButtons().getHeight());
		currentStatusLabel.setSize(getWidth(), 75);
		processesScrollPane.setLocation(gap, currentStatusLabel.getHeight());
		processesScrollPane.setSize(getWidth()-(gap*2), getHeight()-currentStatusLabel.getHeight()-gap);
	}
	public static JPanel getProcessesPanel() {
		return processesPanel;
	}
	public static JScrollPane getProcessesScrollPane() {
		return processesScrollPane;
	}
}
