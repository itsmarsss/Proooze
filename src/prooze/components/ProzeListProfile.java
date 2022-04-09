package prooze.components;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import prooze.Proze;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ProzeListProfile extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProzeListProfile(String name, String time, JPanel container) {
		setPreferredSize(new Dimension(container.getWidth(), 60));
		setBackground(null);
		setLayout(null);
		int width = container.getWidth();
		JLabel processName = new JLabel(name.substring(0, 1).toUpperCase() + name.substring(1));
		processName.setFont(new Font("Sans Serif", Font.PLAIN, 30));
		processName.setBounds(10, 10, width, 40);
		add(processName);
		
		JLabel remove = new JLabel("✖", SwingConstants.CENTER);
		remove.setFont(new Font("Sans Serif", Font.PLAIN, 20));
		remove.setBounds(width-(40+Proze.getGap()), 10, 40, 40);
		//add(remove);
		
		JLabel processTime = new JLabel(time);
		processTime.setFont(new Font("Sans Serif", Font.PLAIN, 15));
		processTime.setBounds(remove.getX()-120, 18, 120, 24);
		add(processTime);
		
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(new Color(177, 177, 177));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(new Color(255, 255, 255));
			}
			
		});
	}
}
