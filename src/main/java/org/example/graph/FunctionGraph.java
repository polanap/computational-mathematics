package org.example.graph;

import org.example.equation.Equation;
import org.example.equation.EquationSystem;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;
import java.awt.*;

public class FunctionGraph extends JFrame {

    public FunctionGraph(Equation equation, double a, double b) {
        super(String.format("График функции %s", equation.toString()));
        XYSeries series = new XYSeries(String.format("y = %s", equation));
        XYSeries seriesY = new XYSeries("y = 0");

        for (double x = Math.ceil(a); x <= Math.floor(b); x += 0.1) {
            double y = equation.calculate(x);
            series.add(x, y);
            seriesY.add(x, 0);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(seriesY);
        JFreeChart chart = ChartFactory.createXYLineChart(
                String.format("График функции %s", equation),
                "X",
                "Y",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
    }

    public FunctionGraph(EquationSystem equation) {
        super(String.format("Система уравнений %s", equation));
        XYSeries series1 = new XYSeries("1", false);
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series2 = new XYSeries("2", false);
        for (double x = -10; x <= 10; x += 0.1) {
            for (double y = -10; y <= 10; y += 0.1) {
                double [] ans = equation.calculate(x, y);
                if (Math.abs(ans[0]) < 0.01) {
                    series1.add(x, y);
                }
                if (Math.abs(ans[1]) < 0.01) {
                    series2.add(x, y);
                }
            }
        }

        dataset.addSeries(series1);
        dataset.addSeries(series2);
        JFreeChart chart = ChartFactory.createScatterPlot(
                String.format("Система уравнений %s", equation),
                "X",
                "Y",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
    }
}
