package org.example.approximation;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.stream.IntStream;

public class LogarithmicApproximation extends Approximation {

    public LogarithmicApproximation(double [] x, double [] y) throws Exception{
        graphColor = Color.BLUE;
        this.x = x;
        this.y = y;
        n = x.length;
        if (Arrays.stream(x).min().getAsDouble()<=0) throw new Exception("значения x не может быть <= 0");
        calculateCoefficients();
        createDeviation();
    }

    private void calculateCoefficients() {
        double sx = Arrays.stream(x).map(xi -> Math.log(xi)).sum();
        double sy = Arrays.stream(y).sum();
        double sxx = Arrays.stream(x)
                .map(x-> Math.log(x)* Math.log(x))
                .sum();
        double sxy = IntStream.range(0, n)
                .mapToDouble(i-> Math.log(x[i])* y[i])
                .sum();
        double det = sxx * n - sx * sx;
        double det1 = sxy * n - sx * sy;
        double det2 = sxx * sy - sx * sxy;
        a = det1 / det;
        b = det2 / det;
    }

    @Override
    public double calculate(double x) {
        return a*Math.log(x)+b;
    }


    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("#.####");
        return String.format("%s * ln(x) + %s", decimalFormat.format(a), decimalFormat.format(b));
    }

    @Override
    public String getStringAnswer(){
        return String.format("\nЛогарифмическая аппроксимирующая функция: %s\n%s\n",
                toString(), super.getStringAnswer());
    }

    @Override
    public void plotGraph(LineChart<Number, Number> lineChart) {
        // Серия для графика функции
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("График функции " + this.toString());

        double left = Arrays.stream(x).min().getAsDouble() - overX;
        double right = Arrays.stream(x).max().getAsDouble() + overX;

        if (left <= 0) {
            left += overX;
            if (left <= 0) {
                left = 0.5;
            }
        }

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
}


