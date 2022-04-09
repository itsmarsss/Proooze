package prooze.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class ProzeBarUI extends BasicScrollBarUI {
	private final Dimension dim = new Dimension();

	private Color trackColor = new Color(47, 49, 54);
	private Color thumbColor = new Color(32, 34, 37);
	private Boolean round;

	public ProzeBarUI(Color trackColor, Color thumbColor, boolean round) {
		this.trackColor = trackColor;
		this.thumbColor = thumbColor;
		this.round = round;
	}
	@Override
	protected JButton createDecreaseButton(int orientation) {
		return new JButton() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Dimension getPreferredSize() {
				return dim;
			}
		};
	}

	@Override
	protected JButton createIncreaseButton(int orientation) {
		return new JButton() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Dimension getPreferredSize() {
				return dim;
			}
		};
	}

	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setPaint(trackColor);
		if(round) {
			g2.fillRoundRect(r.x, r.y, r.width, r.height, r.width, r.width);
			g2.drawRoundRect(r.x, r.y, r.width, r.height, r.width, r.width);
		}else {
			g2.fillRect(r.x, r.y, r.width, r.height);
			g2.drawRect(r.x, r.y, r.width, r.height);
		}
		g2.dispose();
	}

	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setPaint(thumbColor);
		g2.fillRoundRect(r.x, r.y, r.width, r.height, r.width, r.width);
		g2.setPaint(thumbColor);
		g2.drawRoundRect(r.x, r.y, r.width, r.height, r.width, r.width);
		g2.dispose();
	}
	
	@Override
	protected void setThumbBounds(int x, int y, int width, int height) {
		super.setThumbBounds(x, y, width, height);
		scrollbar.repaint();
	}
}
