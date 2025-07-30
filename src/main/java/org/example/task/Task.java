package org.example.task;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.GridPane;
import org.example.functions.Function;
import org.example.interpolation.diviation.FinalDiviation;
import org.example.interpolation.LagrangeInterpolation;
import org.example.interpolation.NewtoneDevidedInterpolation;
import org.example.interpolation.NewtoneFinalInterpolation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task {
    Function function;
    double start;
    double end;
    double h;
    double eps;
    LineChart<Number, Number>[] lineChart = new LineChart[3];


    public Task(Function function, double start, double end, double h, double eps, double x, double y) {
        this.function = function;
        this.start = start;
        this.end = end;
        this.h = h;
        this.eps = eps;
        for (int i = 0; i < 6; i++) {
            lineChart[i] = new LineChart<>(new NumberAxis(), new NumberAxis());
        }
    }

    public String makeAnswer() {
        Arrays.stream(lineChart).forEach(p -> p.getData().clear());
        String ans = "";

        try {
            ans += "Метод Эйлера:\n";

//            LagrangeInterpolation lagrangeInterpolation = new LagrangeInterpolation(x, y);
//            lagrangeInterpolation.plotGraph(lineChart[0]);
//            ans += String.format("Значение L(x) = %s\n", lagrangeInterpolation.calculate(point));

        } catch (Exception e) {
            ans += "Не удалось решить ОДУ методом Эйлера\n";
            ans += e.getMessage() + '\n';
        }

        try {
            ans += "Метод Рунге-Кутта 4-го порядка:\n";

        } catch (Exception e) {
            ans += "Не удалось решить ОДУ методом Рунге-Кутта 4-го порядка\n";
            ans += e.getMessage() + '\n';
        }

        try {
            ans += "Метод Милна:\n";

        } catch (Exception e) {
            ans += "Не удалось решить ОДУ методом Милна\n";
            ans += e.getMessage() + '\n';
        }

        return ans;
    }

    public GridPane createPlotGrid() {
        GridPane plotGrid = new GridPane();
        plotGrid.setPadding(new Insets(10));
        plotGrid.setVgap(10);
        plotGrid.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        Arrays.stream(lineChart).forEach(it -> it.setStyle("-fx-background-color: #f0f0f0;"));
        lineChart[0].setTitle("Метод Эйлера");
        lineChart[1].setTitle("Метод Рунге-Кутта 4-го порядка");
        lineChart[2].setTitle("Метод Милна");

        plotGrid.add(lineChart[0], 0, 0);
        plotGrid.add(lineChart[1], 0, 1);
        plotGrid.add(lineChart[2], 0, 2);
        return plotGrid;
    }

//    public String getStringTable() {
//
//        AsciiTable asciiTable = new AsciiTable();
//        asciiTable.addRule();
//        asciiTable.addRow(makeRow("x", x));
//        asciiTable.addRule();
//        asciiTable.addRow(makeRow("y", y));
//        asciiTable.addRule();
//
//        asciiTable.setTextAlignment(TextAlignment.CENTER);
//        return asciiTable.render() + "\n";
//
//    }


    public static List<Object> makeRow(String title, double[] array) {
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        if (array == null) {
            return new ArrayList<>(0);
        } else {
            int size = array.length;
            List<Object> list = new ArrayList<>(size);
            list.add(title);
            for (double v : array) {
                list.add(decimalFormat.format(v));
            }
            return list;
        }
    }
}
