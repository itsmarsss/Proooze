package prooze.components;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;

public class ProzeButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Color background;
	public ProzeButton(Color background, String text) {
		super(text);
		
		setBack(background);

		setBorder(null);
		setFocusable(false);
		setContentAreaFilled(false);
		setForeground(new Color(255, 255, 255));
	}
	public Color getBack() {
		return background;
	}
	public void setBack(Color back) {
		this.background = back;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if(getModel().isRollover()) {
			g.setColor(getBack().brighter());
		}else {
			g.setColor(getBack());
		}
		g.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight()/10, getHeight()/10);
		super.paintComponent(g);
	}
}
