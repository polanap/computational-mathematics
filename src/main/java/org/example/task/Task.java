package org.example.task;

import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.GridPane;
import org.example.functions.Function;
import org.example.methods.EilerMethod;
import org.example.methods.RungeKuttaMethod;

import java.util.Arrays;

public class Task {
    Function function;
    double start;
    double end;
    double h;
    double eps;
    double y0;
    int count;
    double [] x;
    LineChart<Number, Number>[] lineChart = new LineChart[3];


    public Task(Function function, double start, double end, double h, double eps, double y0) {
        this.function = function;
        this.start = start;
        this.end = end;
        this.h = h;
        this.eps = eps;
        this.y0 = y0;
        count = (int) Math.floor((end - start) / h) + 1;
        for (int i = 0; i < 3; i++) {
            lineChart[i] = new LineChart<>(new NumberAxis(), new NumberAxis());
        }
        x = new double [count];
        for (int i = 0; i < count; i++) {
            x[i] = start + i * h;
        }
    }

    public String makeAnswer() {
        Arrays.stream(lineChart).forEach(p -> p.getData().clear());
        StringBuilder ans = new StringBuilder();

        try {
            ans.append("Метод Эйлера:\n");

            EilerMethod method = new EilerMethod(function, x, y0, h, eps);
            method.plotGraph(lineChart[0]);
            ans.append(method.getStringTable()).append('\n');

        } catch (Exception e) {
            ans.append("Не удалось решить ОДУ методом Эйлера\n");
            ans.append(Arrays.toString(e.getStackTrace())).append('\n');
        }

        try {
            ans.append("Метод Рунге-Кутта 4-го порядка:\n");
            RungeKuttaMethod method = new RungeKuttaMethod(function, x, y0, h, eps);
            method.plotGraph(lineChart[1]);
            ans.append(method.getStringTable()).append('\n');

        } catch (Exception e) {
            ans.append("Не удалось решить ОДУ методом Рунге-Кутта 4-го порядка\n");
            ans.append(e.getMessage()).append('\n');
        }

        try {
            ans.append("Метод Милна:\n");

        } catch (Exception e) {
            ans.append("Не удалось решить ОДУ методом Милна\n");
            ans.append(e.getMessage()).append('\n');
        }

        return ans.toString();
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


//    public static List<Object> makeRow(String title, double[] array) {
//        DecimalFormat decimalFormat = new DecimalFormat("#.###");
//        if (array == null) {
//            return new ArrayList<>(0);
//        } else {
//            int size = array.length;
//            List<Object> list = new ArrayList<>(size);
//            list.add(title);
//            for (double v : array) {
//                list.add(decimalFormat.format(v));
//            }
//            return list;
//        }
//    }
}
