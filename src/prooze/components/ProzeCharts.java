package prooze.components;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

public class ProzeCharts extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProzeCharts(String chartTitle, DefaultPieDataset data) {
		PieDataset dataset = data;
		JFreeChart chart = CreateChart(dataset, chartTitle);
		ChartPanel chartpanel = new ChartPanel(chart);
		chartpanel.setPreferredSize(new java.awt.Dimension(500,400));
		setLayout(new java.awt.BorderLayout());
		add(new ChartPanel(chart));
		setPreferredSize(new java.awt.Dimension(400,300));
		setLocation(30, 30);
		validate();
	}
	private JFreeChart CreateChart(PieDataset dataset, String title) {
		JFreeChart chart = ChartFactory.createPieChart(title, dataset);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setStartAngle(0);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5F);
		return chart;
	}
}
