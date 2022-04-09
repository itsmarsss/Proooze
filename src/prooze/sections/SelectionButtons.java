package prooze.sections;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import prooze.ColorPalette;
import prooze.Proze;
import prooze.components.ProzeButton;

public class SelectionButtons extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int selected;
	
	private ProzeButton summaryButton;
	private ProzeButton currentButton;
	private ProzeButton settingsButton;
	private JPanel spotLight;
	private int gap = Proze.getGap();
	public SelectionButtons() {
		setLayout(null);
		setBackground(null);
		
		Font font = new Font("Sans Serif", Font.PLAIN, 30);
		
		summaryButton = new ProzeButton(ColorPalette.palette.getSummaryColor(), "Summary");
		currentButton = new ProzeButton(ColorPalette.palette.getCurrentColor(), "Current Status");
		settingsButton = new ProzeButton(ColorPalette.palette.getSettingsColor(), "Settings");
		
		spotLight = new JPanel();
		spotLight.setBackground(ColorPalette.palette.getBackgroundColor2());
		
		summaryButton.setFont(font);
		currentButton.setFont(font);
		settingsButton.setFont(font);
		
		setSize();
		setColor();
		
		spotLight.setLocation(currentButton.getX()-gap/2, gap/2);
		selected = 1;
		
		add(summaryButton);
		add(currentButton);
		add(settingsButton);
		add(spotLight);
		
		summaryButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				spotLight.setLocation(summaryButton.getX()-gap/2, gap/2);
				Proze.getSummaryPage().recalibrate();
				Proze.getSummaryPage().setVisible(true);
				Proze.getCurrentPage().setVisible(false);
				Proze.getSettingsPage().setVisible(false);
				selected = 0;
			}
		});
		
		currentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				spotLight.setLocation(currentButton.getX()-gap/2, gap/2);
				Proze.getSummaryPage().setVisible(false);
				Proze.getCurrentPage().setVisible(true);
				Proze.getSettingsPage().setVisible(false);
				selected = 1;
			}
		});
		
		settingsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				spotLight.setLocation(settingsButton.getX()-gap/2, gap/2);
				Proze.getSummaryPage().setVisible(false);
				Proze.getCurrentPage().setVisible(false);
				Proze.getSettingsPage().setVisible(true);
				selected = 2;
			}
		});
	}

	private void setColor() {
		summaryButton.setBack(ColorPalette.palette.getSummaryColor());
		currentButton.setBack(ColorPalette.palette.getCurrentColor());
		settingsButton.setBack(ColorPalette.palette.getSettingsColor());
	}

	public void setSize() {
		setSize(Proze.getPanel().getWidth(), Proze.getPanel().getHeight()/5);
		
		int buttonWidth = (getWidth()-(gap*4))/3;
		int buttonHeight = getHeight()-(gap*2);
		
		summaryButton.setSize(buttonWidth, buttonHeight);
		currentButton.setSize(buttonWidth, buttonHeight);
		settingsButton.setSize(buttonWidth, buttonHeight);
		
		spotLight.setSize(buttonWidth+gap, buttonHeight+gap);
		
		summaryButton.setLocation(gap, gap);
		currentButton.setLocation(summaryButton.getX()+summaryButton.getWidth()+gap, gap);
		settingsButton.setLocation(currentButton.getX()+currentButton.getWidth()+gap, gap);
		
		switch(selected) {
		case 0:
			spotLight.setLocation(summaryButton.getX()-gap/2, gap/2);
			break;
		case 1:
			spotLight.setLocation(currentButton.getX()-gap/2, gap/2);
			break;
		case 2:
			spotLight.setLocation(settingsButton.getX()-gap/2, gap/2);
			break;
		}

		summaryButton.repaint();
		summaryButton.revalidate();
		
		currentButton.repaint();
		currentButton.revalidate();
		
		settingsButton.repaint();
		settingsButton.revalidate();
		
		repaint();
		revalidate();
		
		Proze.getFrame().repaint();
		Proze.getFrame().revalidate();
	}
	public ProzeButton getSummaryButton() {
		return summaryButton;
	}
	public ProzeButton getCurrentButton() {
		return currentButton;
	}
	public ProzeButton getSettingsButton() {
		return settingsButton;
	}
}
