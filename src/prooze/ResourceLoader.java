package prooze;

import javax.swing.ImageIcon;

public class ResourceLoader {
	public static ResourceLoader loader = new ResourceLoader();
	
	private ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/icon.png"));
	private ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("resources/logo.jpg"));

	public ImageIcon getIcon() {
		return icon;
	}

	public ImageIcon getLogo() {
		return logo;
	}

}
