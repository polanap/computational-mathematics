package org.example.approximation;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public abstract class Approximation implements Graphical{
    double a;
    double b;
    double [] x;
    double [] y;
    double [] e;
    int n;

    final double overX = 3;
    Color graphColor;

    public double getStandardDeviation(){
        return Math.sqrt(Arrays.stream(e)
                .map(e -> e*e)
                .sum()/n);
    }

    protected void createDeviation() {
        e = IntStream.range(0, x.length)
                .mapToDouble(i->Math.abs(y[i] - calculate(x[i])))
                .toArray();
    }

    abstract double calculate(double x);

    @Override
    public void plotGraph(LineChart<Number, Number> lineChart) {

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("График функции "+ this.toString());

        double overX = 3;
        double left = Arrays.stream(x).min().getAsDouble()-overX;
        double right = Arrays.stream(x).max().getAsDouble()+overX;

        double step = (right-left)/100;
        for (double xi = left; xi <= right; xi += step) {
            double yi = calculate(xi);
            XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(xi, yi);
            Circle point = new Circle(1);
            point.setFill(graphColor);
            dataPoint.setNode(point);
            dataPoint.setNode(point);
            series.getData().add(dataPoint);
        }

        XYChart.Series<Number, Number> series0 = new XYChart.Series<>();
        series0.setName("Изначальные точки");
        for (int i = 0; i < n; i++) {
            XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(x[i], y[i]);
            Circle point = new Circle(5);
            point.setFill(Color.BLUE);
            dataPoint.setNode(point);
            dataPoint.setNode(point);
            series0.getData().add(dataPoint);
        }

        lineChart.getData().add(series);
        lineChart.getData().add(series0);
    }

    public String getStringAnswer(){
        return String.format("%s\nСреднеквадратичное отклонение: %s\n", getStringTable(), getStandardDeviation());
    }

    public String getStringTable() {
        AsciiTable asciiTable = new AsciiTable();
//        asciiTable.addRule();
        asciiTable.addRow(makeRow(" x ", x));
        asciiTable.addRow(makeRow(" y ", y));
        asciiTable.addRow(makeRow("eps", e));
//        asciiTable.addRule();
        asciiTable.setTextAlignment(TextAlignment.CENTER);
        System.out.println(asciiTable.render());
        return asciiTable.render();
    }

    public static List<Object> makeRow(String title, double [] array) {
        if (array==null) {
            return new ArrayList(0);
        } else {
            int size = array.length;
            List<Object> list = new ArrayList(size);
            list.add(title);
            for(int i = 0; i < size; i++) {
                list.add(array[i]);
            }
            return list;
        }
    }
}
