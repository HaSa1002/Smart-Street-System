package de.verkehr.diagram;

import de.verkehr.data.Sensor;
import de.verkehr.data.SensorData;
import de.verkehr.data.SensorManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.IntervalBarRenderer;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Raffael on 18.06.2016.
 */
public class Diagram extends ApplicationFrame {

    JFreeChart chart;
    private Plot plot;

    public Diagram(String title) {
        super(title);
        plot = new FastScatterPlot();
        CategoryDataset dataset = createDataset(SensorManager.getSensor("S_01"));
        this.chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(1400, 900));
        setContentPane(chartPanel);
    }

    private CategoryDataset createDataset(Sensor sensor) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<Integer, Integer> dataMap = count(sensor.getSensorData());

        for (int i = 0; i < 24; i++) {
            dataset.addValue(dataMap.get(i), "autos", i + " Uhr");
        }

        return dataset;
    }

    /*
        private Map<Integer, SensorData> cluster(List<SensorData> dataList) {
            Map<Integer, SensorData> clusterMap = new HashMap<>();
            for(SensorData data : dataList) {
                clusterMap.put(data.getTime().getHours(), data);
            }
            return clusterMap;
        }
    */
    private Map<Integer, Integer> count(List<SensorData> data) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < data.size(); i++) {
            int hour = data.get(i).getTime().getHours();
            if (map.containsKey(hour)) {
                int times = map.get(hour) + 1;
                map.put(hour, times);
            } else {
                map.put(hour, 1);
            }
        }

        return map;
    }

    private JFreeChart createChart(final CategoryDataset dataset) {

        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
                "Autos/Stunde",         // chart title
                "Uhrzeit",               // domain axis label
                "Autos",                  // range axis label
                dataset,                  // data
                PlotOrientation.VERTICAL, // orientation
                true,                     // include legend
                true,                     // tooltips?
                false                     // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);

        // set up gradient paints for series...
        final GradientPaint gp0 = new GradientPaint(
                0.0f, 0.0f, Color.blue,
                0.0f, 0.0f, Color.lightGray
        );
        final GradientPaint gp1 = new GradientPaint(
                0.0f, 0.0f, Color.green,
                0.0f, 0.0f, Color.lightGray
        );
        final GradientPaint gp2 = new GradientPaint(
                0.0f, 0.0f, Color.red,
                0.0f, 0.0f, Color.lightGray
        );
        renderer.setSeriesPaint(0, gp0);
        renderer.setSeriesPaint(1, gp1);
        renderer.setSeriesPaint(2, gp2);

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
        // OPTIONAL CUSTOMISATION COMPLETED.

        return chart;

    }
}

