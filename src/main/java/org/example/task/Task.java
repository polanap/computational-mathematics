package org.example.task;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.GridPane;
import org.example.interpolation.diviation.FinalDiviation;
import org.example.interpolation.LagrangeInterpolation;
import org.example.interpolation.NewtoneDevidedInterpolation;
import org.example.interpolation.NewtoneFinalInterpolation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task {
    double[] x;
    double[] y;
    double point;
    LineChart<Number, Number>[] lineChart = new LineChart[6];


    public Task(double[] x, double[] y, double point) {
        this.x = x;
        this.y = y;
        this.point = point;
        for (int i = 0; i < 6; i++) {
            lineChart[i] = new LineChart<>(new NumberAxis(), new NumberAxis());
        }
    }

    public String makeAnswer() {
        Arrays.stream(lineChart).forEach(p -> p.getData().clear());
        FinalDiviation finalDiviation = new FinalDiviation(y);
        String ans = "";
        ans += "Значения Х и У:\n";
        ans += getStringTable();
        ans += "Таблица конечных разностей:\n";
        ans += finalDiviation.getStringTable();

        try {
            ans += "Многочлен Лагранжа:\n";
            LagrangeInterpolation lagrangeInterpolation = new LagrangeInterpolation(x, y);
            lagrangeInterpolation.plotGraph(lineChart[0]);
            ans += String.format("Значение L(x) = %s\n", lagrangeInterpolation.calculate(point));
        } catch (Exception e) {
            ans += "Не удалось посчитать интерполяционный многочлен по формуле Лагранжа \n";
            ans += e.getMessage() + '\n';
        }

        try {
            ans += "Многочлен Ньютона с разделенными разностями:\n";
            NewtoneDevidedInterpolation newtoneDevidedInterpolation = new NewtoneDevidedInterpolation(x, y);
            newtoneDevidedInterpolation.plotGraph(lineChart[1]);
            ans += String.format("Значение N(x) = %s\n", newtoneDevidedInterpolation.calculate(point));
        } catch (Exception e) {
            ans += "Не удалось посчитать интерполяционный многочлен по формуле Ньютона для разделенных разностей \n";
            ans += e.getMessage() + '\n';
        }

        try {
            ans += String.format("Многочлен Ньютона с конечными разностями (%s формула):\n", (point <= (x[x.length - 1] + x[0]) / 2) ? 1 : 2);
            NewtoneFinalInterpolation newtoneFinalInterpolation = new NewtoneFinalInterpolation(x, y);
            newtoneFinalInterpolation.plotGraph(lineChart[2]);
            ans += String.format("Значение N(x) = %s\n", newtoneFinalInterpolation.calculate(point));
        } catch (Exception e) {
            ans += "Не удалось посчитать интерполяционный многочлен по формуле Ньютона для конечных разностей \n";
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
        lineChart[0].setTitle("Многочлен Лагранжа");
        lineChart[1].setTitle("Многочлен Ньютона с разделенными разностями");
        lineChart[2].setTitle("Многочлен Ньютона с конечными разностями");

        plotGrid.add(lineChart[0], 0, 0);
        plotGrid.add(lineChart[1], 0, 1);
        plotGrid.add(lineChart[2], 0, 2);
        return plotGrid;
    }

    public String getStringTable() {

        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow(makeRow("x", x));
        asciiTable.addRule();
        asciiTable.addRow(makeRow("y", y));
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
            for (double v : array) {
                list.add(decimalFormat.format(v));
            }
            return list;
        }
    }
}
