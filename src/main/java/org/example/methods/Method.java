package org.example.methods;

import org.example.equation.Equation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Method implements Calculatable {
    Equation equantion;
    double accuracy;
    double sum = 0;
    int n = 4;
    int MAX_ITERATION_COUNT = 1000;
    double previousSum = 0;
    Map<Integer, Double> previousSumMap = new HashMap<Integer, Double>();

    public Method(Equation equantion, double accuracy) {
        this.equantion = equantion;
        this.accuracy = accuracy;
    }

    @Override
    public double calculate(double a, double b) throws Exception  {
        return sum;
    }

    public String printResult(){
        return String.format("Значение интеграла: %s, число разбиения интервала: %s \n", sum, n);
    }

    public double getCurrentAccuracy(){
        return Math.abs(sum - previousSum)/3;
    }
}
