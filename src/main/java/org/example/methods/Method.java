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

public abstract class Method implements Graphical {
    Function function;
    double[] x;
    double[] y;
    double h;
    double eps;
    int count;

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
        for (int i = 0; i < count; i++) {
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

    public String getStringTable() {

        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow(makeRow("i", IntStream.rangeClosed(0, count-1)
                .mapToDouble(it -> it)
                .toArray()));
        asciiTable.addRule();
        asciiTable.addRow(makeRow("xi", x));
        asciiTable.addRule();
        asciiTable.addRow(makeRow("yi", y));
        asciiTable.addRule();
        asciiTable.addRow(makeRow("f(xi,yi)", IntStream.rangeClosed(0, count-1)
                .mapToDouble(i->function.derivative(x[i], y[i]))
                .toArray()));
        asciiTable.addRule();
        asciiTable.addRow(makeRow("y(xi)", IntStream.rangeClosed(0, count-1)
                .mapToDouble(i->function.calculate(x[i], x[0], y[0]))
                .toArray()));
        asciiTable.addRule();

        asciiTable.setTextAlignment(TextAlignment.CENTER);
        return asciiTable.render() + "\n";

    }


    public static List<Object> makeRow(String title, double[] array) {
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
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

}
