package org.example.methods;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.example.functions.Function;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Math.abs;

public abstract class Method implements Graphical {
    Function function;
    double[] x;
    double[] y;
    double h;
    double eps;
    double start;
    double end;
    double y0;
    int maxItteration = 1000;
    double[] errors;
    int order = 1;

    final double overX = 0.3;
    Color graphColor;

    protected abstract void calculate();

    @Override
    public void plotGraph(LineChart<Number, Number> lineChart) {
        // Серия для графика функции
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("y = f(x)");

        double left = Arrays.stream(x).min().getAsDouble() - overX;
        double right = Arrays.stream(x).max().getAsDouble() + overX;

        double step = (right - left) / 100;
        for (double xi = left; xi <= right; xi += step) {
            double yi = function.calculate(xi, x[0], y[0]);
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
        series0.setName("Точки решения");
        for (int i = 0; i < x.length; i++) {
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

//    public String getStringTable() {
//
//        AsciiTable asciiTable = new AsciiTable();
//        asciiTable.addRule();
//        asciiTable.addRow(makeRow("i", IntStream.rangeClosed(0, count-1)
//                .mapToDouble(it -> it)
//                .toArray()));
//        asciiTable.addRule();
//        asciiTable.addRow(makeRow("xi", x));
//        asciiTable.addRule();
//        asciiTable.addRow(makeRow("yi", y));
//        asciiTable.addRule();
//        asciiTable.addRow(makeRow("f(xi,yi)", IntStream.rangeClosed(0, count-1)
//                .mapToDouble(i->function.derivative(x[i], y[i]))
//                .toArray()));
//        asciiTable.addRule();
//        asciiTable.addRow(makeRow("y(xi)", IntStream.rangeClosed(0, count-1)
//                .mapToDouble(i->function.calculate(x[i], x[0], y[0]))
//                .toArray()));
//        asciiTable.addRule();
//
//        asciiTable.setTextAlignment(TextAlignment.CENTER);
//        return asciiTable.render() + "\n";
//
//    }

    public String getStringTable() {
        DecimalFormat decimalFormat = new DecimalFormat("#.#####");
        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();

        int count = x.length;

        asciiTable.addRow("i", "xi", "yi", "f(xi,yi)", "y(xi)", "error[i]");
        asciiTable.addRule();

        for (int i = 0; i < count; i++) {
            asciiTable.addRow(
                    i,
                    decimalFormat.format(x[i]),
                    decimalFormat.format(y[i]),
                    decimalFormat.format(function.derivative(x[i], y[i])),
                    decimalFormat.format(function.calculate(x[i], x[0], y[0])),
                    decimalFormat.format(errors[i])
            );
            asciiTable.addRule();
        }

        asciiTable.setTextAlignment(TextAlignment.CENTER);
        return asciiTable.render() + "\n";
    }


    public static List<Object> makeRow(String title, double[] array) {
        DecimalFormat decimalFormat = new DecimalFormat("#.#####");
        if (array == null) {
            return new ArrayList<>(0);
        } else {
            int size = array.length;
            List<Object> list = new ArrayList<>(size);
            list.add(title);
            for (int k = 0; k < size; k++) {
                list.add(decimalFormat.format(array[k]));
            }
            return list;
        }
    }

    public double[] makeXPoints(double h) {
        double[] xh;
        int count = getCount(h);
        xh = new double[count];
        for (int i = 0; i < count; i++) {
            xh[i] = start + i * h;
        }
        return xh;
    }

    public double[] runge(double[] y2h, double[] yh) {
        double[] errors = new double[y2h.length];
        for (int i = 0; i < y2h.length; i++) {
            int idxH = i * 2;
            double err = Math.abs(yh[idxH] - y2h[i]) / (Math.pow(2, order) - 1);
            errors[i] = err;
        }
        return errors;
    }

    public int getCount(double h) {
        return (int) Math.floor((end - start) / h) + 1;
    }

}
