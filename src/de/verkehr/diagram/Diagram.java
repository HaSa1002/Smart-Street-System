package de.verkehr.diagram;

import de.verkehr.data.Sensor;
import de.verkehr.data.SensorData;
import de.verkehr.data.SensorManager;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.FastScatterPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.IntervalBarRenderer;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Raffael on 18.06.2016.
 */
public class Diagram {

    JFreeChart chart;
    private Plot plot;

    public void createChart() {
        plot = new FastScatterPlot();
        CategoryDataset dataset = createDataset(SensorManager.getSensor("S_01"));
    }


    public void show() {
        ChartFrame frame = new ChartFrame("Test", this.chart);
        frame.show();
    }

    private CategoryDataset createDataset(Sensor sensor) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<Integer, Integer> dataMap = count(sensor.getSensorData());

        for(int i = 0; i < 24; i++) {
            dataset.addValue(dataMap.get(i), "gesamt", i + " Uhr");
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
        for(int i = 0; i < data.size(); i++) {
            int hour = data.get(i).getTime().getHours();
            if(map.containsKey(hour)) {
                int times = map.get(hour) + 1;
                map.put(hour, times);
            } else {
                map.put(hour, 1);
            }
        }

        return map;
    }
}

