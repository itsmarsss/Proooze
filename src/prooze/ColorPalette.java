package prooze;

import java.awt.Color;

public class ColorPalette {
	public static ColorPalette palette = new ColorPalette();
	
	private final Color summary = new Color(244, 123, 2);
	private final Color current = new Color(13, 62, 204);
	private final Color settings = new Color(101, 103, 113);
	private final Color main = new Color(13, 62, 204);
	private final Color background = new Color(196, 220, 241);
	private final Color background2 = new Color(224, 236, 248);
	
	public Color getSummaryColor() {
		return summary;
	}
		
	public Color getCurrentColor() {
		return current;
	}
	
	public Color getSettingsColor() {
		return settings;
	}
	
	public Color getMainColor() {
		return main;
	}
	
	public Color getBackgroundColor() {
		return background;
	}
	
	public Color getBackgroundColor2() {
		return background2;
	}
}
