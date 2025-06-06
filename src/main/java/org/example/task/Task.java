package org.example.task;

import javafx.scene.chart.LineChart;
import org.example.approximation.LinearApproximation;

public class Task {
    double [] x;
    double [] y;
    LineChart<Number, Number> lineChart;


    public Task(double [] x, double [] y, LineChart<Number, Number> lineChart) {
        this.x = x;
        this.y = y;
        this.lineChart = lineChart;
    }

    public String makeAnswer(){
        lineChart.getData().clear();
        String ans = "";
        ans+="Получение аппроксимирующей функции...\n";
        try {
            LinearApproximation linear = new LinearApproximation(x, y);
            ans+=linear.getStringAnswer();
            linear.plotGraph(lineChart);
        } catch (Exception e){
            ans+="Не удалось посчитать\n";
            ans+=e.getMessage()+'\n';
        }
        return ans;
    }
}
