package view;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.HorizontalAlignment;

import javafx.collections.ObservableList;
import model.DowJonesData;

/**
 * Window class for the Dow Jones Industrial Average Graph.
 * <p>
 * To use this class, it must be called by the Window Facade 
 * through the generateGraphPane method. This method will
 * return a BorderPane that may be used as content for the
 * graphTab in the MainWindow class.
 * 
 * @author Andrew
 */
public class GraphWindow {

	/**
	 * After being provided with a list of DowJonesData,
	 * a dataset will be generated out of provided data. Then using 
	 * the created dataset, will create a chart. This chart object will
	 * then be placed inside of a ChartViewer, which will reside within
	 * the Center frame of the Graph Window's BorderPane. 
	 * <p>
	 * Upon calling createGraphPane (which will require the 
	 * list of data to be passed to it), the newly generated
	 * graph will be passed into a ChartViewer and placed inside
	 * of the graphWindowBorderPane, and will then return the pane.
	 * 
	 * @param djDataList
	 */
	public GraphWindow() {}
	
	/** Creates a chart from the provided dataset. Returns newly created chart. */
	private static JFreeChart createChart(XYDataset dataset) {
		
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Dow Jones Industrial Average - 10 Years", // title
				"Dates", // x-axis label
				"Values", // y-axis label
				dataset);
		
		String fontName = "Palatino";
		chart.getTitle().setFont(new Font(fontName, Font.BOLD, 18));
		/*chart.addSubtitle(new TextTitle("Source: https://fred.stlouisfed.org/series/DJIA/downloaddata",
				new Font(fontName, Font.PLAIN, 14)));*/
		chart.setBackgroundPaint(Color.DARK_GRAY);
		chart.getTitle().setPaint(Color.LIGHT_GRAY);
		chart.getTitle().setPadding(10, 0, 5, 0);
		
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setDomainPannable(true);
		plot.setRangePannable(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);
		plot.setBackgroundPaint(Color.GRAY);
		plot.getDomainAxis().setLowerMargin(0.0);;
		plot.getDomainAxis().setLabelFont(new Font(fontName, Font.BOLD, 14));
		plot.getDomainAxis().setTickLabelFont(new Font(fontName, Font.PLAIN, 14));
		plot.getDomainAxis().setLabelPaint(Color.WHITE);
		plot.getDomainAxis().setTickLabelPaint(Color.WHITE);
		plot.getDomainAxis().setTickMarkPaint(Color.WHITE);
		plot.getRangeAxis().setLabelFont(new Font(fontName, Font.BOLD, 14));
		plot.getRangeAxis().setTickLabelFont(new Font(fontName, Font.PLAIN, 12));
		plot.getRangeAxis().setLabelPaint(Color.WHITE);
		plot.getRangeAxis().setTickLabelPaint(Color.WHITE);
		plot.getRangeAxis().setTickMarkPaint(Color.WHITE);
		chart.getLegend().setItemFont(new Font(fontName, Font.PLAIN, 14));
		chart.getLegend().setFrame(BlockBorder.NONE);
		chart.getLegend().setHorizontalAlignment(HorizontalAlignment.CENTER);
		chart.getLegend().setBackgroundPaint(Color.DARK_GRAY);
		chart.getLegend().setItemPaint(Color.WHITE);
		XYItemRenderer r =  plot.getRenderer();
		if(r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(false);
			renderer.setDrawSeriesLineAsPath(true);
			// set the default stroke for all series
			renderer.setAutoPopulateSeriesStroke(false);
			renderer.setBaseStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND,
					BasicStroke.JOIN_BEVEL), false);
			renderer.setSeriesPaint(0, Color.blue);
		}
		
		return chart;
	}
	
	/** Creates a dataset consisting of one series of data, derived from the provided list of data */
	private static XYDataset createDataset(ObservableList<DowJonesData> djDataList) {
	
		TimeSeries dataSeries = new TimeSeries("DJIA Data Series");
		
		// For every item in the list, create a new regular time period (day)
		// using the information provided in the Date by the DowJonesData item.
		// Then, add the newly created day alongside the corresponding value to
		// the Time Series.
		for(DowJonesData item : djDataList) {
			RegularTimePeriod day = new Day(item.getDate());
			dataSeries.add(day, item.getValue());
		}
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(dataSeries);
		
		return dataset;
	}
	
	/**
	 * Upon calling createGraphPane (which will require the 
	 * list of data to be passed to it), the newly generated
	 * graph will be passed into a ChartViewer and placed inside
	 * of the graphWindowBorderPane, and will then return the pane.
	 * 
	 * @param dataList
	 * @return graphWindowBorderPane
	 */
	public ChartViewer generateGraphViewer(ObservableList<DowJonesData> dataList) {
		XYDataset dataset = createDataset(dataList);
		JFreeChart chart = createChart(dataset);
		ChartViewer viewer = new ChartViewer(chart);
		
		return viewer;
	}
}
