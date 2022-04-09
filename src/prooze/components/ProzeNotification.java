package prooze.components;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import prooze.Proze;
import prooze.ResourceLoader;

public class ProzeNotification {
	public void displayTray(String title, String subtitle) throws AWTException {
        SystemTray tray = SystemTray.getSystemTray();

        Image image = ResourceLoader.loader.getIcon().getImage();
        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("Get Back to Work You Sugar Honey Iced-Tea!");
        tray.add(trayIcon);

        trayIcon.displayMessage(title, subtitle, MessageType.INFO);
        trayIcon.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Proze.getFrame().setVisible(true);
				Proze.getFrame().setExtendedState(JFrame.NORMAL);
				tray.remove(trayIcon);
				Proze.getSelectionButtons().getCurrentButton().doClick();
			}
        	
        });
    }
}
