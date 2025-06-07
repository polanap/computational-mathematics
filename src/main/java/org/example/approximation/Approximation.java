package org.example.approximation;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public abstract class Approximation implements Graphical{
    double a;
    double b;
    double c;
    double d;
    double [] x;
    double [] y;
    double [] e;
    int n;

    final double overX = 1;
    Color graphColor;

    public double getStandardDeviation(){
        double zeroBound = Math.pow(10, -10);
        double stdDeviation = Math.sqrt(Arrays.stream(e)
                .map(e -> e*e)
                .sum()/n);
        if(stdDeviation <= zeroBound){
            return 0;
        }
        return stdDeviation;
    }

    protected void createDeviation() {
        e = IntStream.range(0, x.length)
                .mapToDouble(i->Math.abs(y[i] - calculate(x[i])))
                .toArray();
    }

    abstract double calculate(double x);

    @Override
    public void plotGraph(LineChart<Number, Number> lineChart) {
        // Серия для графика функции
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("График функции " + this.toString());

        double left = Arrays.stream(x).min().getAsDouble() - overX;
        double right = Arrays.stream(x).max().getAsDouble() + overX;

        double step = (right - left) / 100;
        for (double xi = left; xi <= right; xi += step) {
            double yi = calculate(xi);
            XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(xi, yi);
            Circle point = new Circle(1);
            point.setFill(graphColor);
            dataPoint.setNode(point);
            series.getData().add(dataPoint);
        }

        // Добавление серии с графиком функции
        lineChart.getData().add(series);

        // Серия для изначальных точек
        XYChart.Series<Number, Number> series0 = new XYChart.Series<>();
        series0.setName("Изначальные точки");
        for (int i = 0; i < n; i++) {
            XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(x[i], y[i]);
            Circle point = new Circle(5);
            point.setFill(Color.BLUE);
            dataPoint.setNode(point);
            series0.getData().add(dataPoint);
        }

        // Добавление серии с изначальными точками в график
        lineChart.getData().add(series0);

        // Установка стиля для отключения линий в серии с изначальными точками
        series0.getNode().setStyle("-fx-stroke: transparent;"); // Отключение линий
    }



    public String getStringAnswer(){
        return String.format("%s\nСреднеквадратичное отклонение: %s\n", getStringTable(), getStandardDeviation());
    }

    public String getStringTable() {
        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow(makeRow(" x ", x));
        asciiTable.addRule();
        asciiTable.addRow(makeRow(" y ", y));
        asciiTable.addRule();
        asciiTable.addRow(makeRow("\u03C6(x)", Arrays.stream(x).map(this::calculate).toArray()));
        asciiTable.addRule();
        asciiTable.addRow(makeRow("eps", e));
        asciiTable.addRule();
        asciiTable.setTextAlignment(TextAlignment.CENTER);
//        System.out.println(asciiTable.render());
        return asciiTable.render();
    }

    public static List<Object> makeRow(String title, double [] array) {
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        if (array==null) {
            return new ArrayList(0);
        } else {
            int size = array.length;
            List<Object> list = new ArrayList(size);
            list.add(title);
            for(int i = 0; i < size; i++) {
                list.add(decimalFormat.format(array[i]));
            }
            return list;
        }
    }

    static double det3degree(double[][] m) {
        return m[0][0] * m[1][1] * m[2][2]
                + m[0][1] * m[1][2] * m[2][0]
                + m[0][2] * m[1][0] * m[2][1]
                - m[0][2] * m[1][1] * m[2][0]
                - m[0][1] * m[1][0] * m[2][2]
                - m[0][0] * m[1][2] * m[2][1];
    }

    static double det4degree(double[][] m) {
        double res = 0;
        for (int c = 0; c < 4; c++) {
            double[][] minor = new double[3][3];
            for (int i = 1; i < 4; i++) {
                int mi = i - 1;
                int mj = 0;
                for (int j = 0; j < 4; j++) {
                    if (j == c) continue;
                    minor[mi][mj++] = m[i][j];
                }
            }
            res += ((c % 2) == 0 ? 1 : -1) * m[0][c] * det3degree(minor);
        }
        return res;
    }
}
