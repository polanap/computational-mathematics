package org.example.methods;

import org.example.equation.Equation;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Method implements Calculatable {
    Equation equantion;
    double accuracy;
    double sum = 0;
    int n = 4;
    int MAX_ITERATION_COUNT = 28;
    double previousSum = 0;
    Map<Integer, Double> previousSumMap = new HashMap<Integer, Double>();

    public Method(Equation equantion, double accuracy) {
        this.equantion = equantion;
        this.accuracy = accuracy;
    }

    public double runge(){
        return Math.abs(sum - previousSum);
    }

    @Override
    public double calculate(double a, double b) throws Exception  {
        return sum;
    }

    public String printResult(){
        String[] parts = String.valueOf(accuracy).split("\\.");
        int signCount = parts.length > 1 ? parts[1].length() : 0;

        // Форматируем число для вывода
        StringBuilder format = new StringBuilder("#.");
        for (int i = 0; i < signCount; i++) {
            format.append("#");
        }

        DecimalFormat decimalFormat = new DecimalFormat(format.toString());
        String newNum = decimalFormat.format(sum);
        return String.format("Значение интеграла: %s, число разбиения интервала: %s \n", newNum, n);
    }

    public double getCurrentAccuracy(){
        return runge();
    }

}
