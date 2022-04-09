package prooze.sections;

import java.awt.Color;
import java.awt.Image;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jfree.data.general.DefaultPieDataset;

import prooze.Proze;
import prooze.ResourceLoader;
import prooze.components.ProzeCharts;
import prooze.components.ProzePanel;
import prooze.processes.ProcessInfo;

public class SummaryPage extends ProzePanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SummaryPage(Color background) {
		super(background);
		setLayout(null);

		setSize();
	}
	public void setSize() {
		int gap = Proze.getGap();
		setSize(Proze.getPanel().getWidth()-(gap*2), Proze.getPanel().getHeight()-Proze.getSelectionButtons().getHeight()-(gap*3));
		setLocation(gap, Proze.getSelectionButtons().getHeight());
		
		recalibrate();
	}
	private JLabel image = new JLabel();
	public void recalibrate() {
		removeAll();
		revalidate();
		repaint();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		DefaultPieDataset result = new DefaultPieDataset();
		LinkedList<ProcessInfo>currentRunning = Proze.getCurrentRunning();
		long sum = 0;
		for(ProcessInfo i:currentRunning) {
			sum+=i.getDuration();
		}
		System.out.println(sum);
		for(ProcessInfo i:currentRunning) {
			int percent = (int)Math.floor(((double)i.getDuration()/(double)sum)*100D);
			result.setValue(i.getName(), percent);
		}

		ProzeCharts chart = new ProzeCharts("Today", result);
		
		chart.setLocation(0, 0);
		chart.setSize(getHeight(), getHeight());
		int width = getWidth()-chart.getWidth();
		int height = getHeight();
		image.setSize(width, height);
		image.setLocation(chart.getWidth(), 0);
		
		Image newImage = ResourceLoader.loader.getLogo().getImage().getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_DEFAULT);
		image.setIcon(new ImageIcon(newImage));
		
		chart.revalidate();
		chart.repaint();
		add(chart);
		add(image);
		revalidate();
		repaint();
	}

}
