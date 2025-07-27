package org.example.task;

import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.GridPane;
import org.example.approximation.*;
import org.example.interpolation.FinalDiviation;
import org.example.interpolation.LagrangeInterpolation;

import java.util.Arrays;

public class Task {
    double [] x;
    double [] y;
    LineChart<Number, Number> [] lineChart = new LineChart[6];


    public Task(double [] x, double [] y) {
        this.x = x;
        this.y = y;
        for (int i = 0; i < 6; i++) {
            lineChart[i] = new LineChart<>(new NumberAxis(), new NumberAxis());
        }
    }

    public String makeAnswer(){
        Arrays.stream(lineChart).forEach(p -> p.getData().clear());
        FinalDiviation finalDiviation = new FinalDiviation(y);
        String ans = "";
        ans += "Таблица конечных разностей:\n";
        ans += finalDiviation.getStringTable();
        LagrangeInterpolation lagrangeInterpolation = new LagrangeInterpolation(x, y);
        lagrangeInterpolation.plotGraph(lineChart[0]);



//        String bestApproximationAns = "";
//        double bestDeviation = Double.MAX_VALUE;
//        String ans = "";
//        try {
//            LinearApproximation linear = new LinearApproximation(x, y);
//            ans+=linear.getStringAnswer();
//            linear.plotGraph(lineChart[0]);
//            bestDeviation = linear.getStandardDeviation();
//            bestApproximationAns = linear.toString();
//        } catch (Exception e){
//            ans+="Не удалось посчитать линейную функцию\n";
//            ans+=e.getMessage()+'\n';
//        }
//
//        try {
//            SecondDegreePolynomialApproximation polynomial2degree = new SecondDegreePolynomialApproximation(x, y);
//            ans+=polynomial2degree.getStringAnswer();
//            polynomial2degree.plotGraph(lineChart[1]);
//            if (bestDeviation > polynomial2degree.getStandardDeviation()){
//                bestApproximationAns = polynomial2degree.toString();
//                bestDeviation = polynomial2degree.getStandardDeviation();
//            }
//        } catch (Exception e){
//            ans+="Не удалось посчитать полином второй степени\n";
//            ans+=e.getMessage()+'\n';
//        }
//
//        try {
//            ThirdDegreePolynomialApproximation polynomial3degree = new ThirdDegreePolynomialApproximation(x, y);
//            ans+=polynomial3degree.getStringAnswer();
//            polynomial3degree.plotGraph(lineChart[2]);
//            if (bestDeviation > polynomial3degree.getStandardDeviation()){
//                bestApproximationAns = polynomial3degree.toString();
//                bestDeviation = polynomial3degree.getStandardDeviation();
//            }
//        } catch (Exception e){
//            ans+="Не удалось посчитать полином третей степени\n";
//            ans+=e.getMessage()+'\n';
//        }
//
//        try {
//            ExponentialApproximation exponential = new ExponentialApproximation(x, y);
//            ans+=exponential.getStringAnswer();
//            exponential.plotGraph(lineChart[3]);
//            if (bestDeviation > exponential.getStandardDeviation()){
//                bestApproximationAns = exponential.toString();
//                bestDeviation = exponential.getStandardDeviation();
//            }
//        } catch (Exception e){
//            ans+="Не удалось посчитать экспоненциальную аппроксимацию\n";
//            ans+=e.getMessage()+'\n';
//        }
//
//        try {
//            PowerApproximation power = new PowerApproximation(x, y);
//            ans+=power.getStringAnswer();
//            power.plotGraph(lineChart[4]);
//            if (bestDeviation > power.getStandardDeviation()){
//                bestApproximationAns = power.toString();
//                bestDeviation = power.getStandardDeviation();
//            }
//        } catch (Exception e){
//            ans+="Не удалось посчитать степенную аппроксимацию\n";
//            ans+=e.getMessage()+'\n';
//        }
//
//        try {
//            LogarithmicApproximation logarithmicApproximation = new LogarithmicApproximation(x, y);
//            ans+=logarithmicApproximation.getStringAnswer();
//            logarithmicApproximation.plotGraph(lineChart[5]);
//            if (bestDeviation > logarithmicApproximation.getStandardDeviation()){
//                bestApproximationAns = logarithmicApproximation.toString();
//                bestDeviation = logarithmicApproximation.getStandardDeviation();
//            }
//        } catch (Exception e){
//            ans+="Не удалось посчитать логарифмическую аппроксимацию\n";
//            ans+=e.getMessage()+'\n';
//        }
//
//        ans+=String.format("Лучшая аппроксимация: %s (среднеквадратичное отклонение = %s)\n",
//                bestApproximationAns, bestDeviation);

        return ans;
    }

    public GridPane createPlotGrid(){
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
}
