package org.example.methods;

import org.example.equation.Equation;

public class Method implements Calculatable {
    Equation equantion;
    double accuracy;
    double x = 0;
    double oldX = 0;
    int n = 0;
    int MAX_ITERATION_COUNT = 1000;

    public Method(Equation equantion, double accuracy) {
        this.equantion = equantion;
        this.accuracy = accuracy;
    }

    @Override
    public double calculate(double a, double b) throws Exception  {
        return 0;
    }

    public String printResult(){
        return String.format("Значение корня: %s, Значение функции в корне: %s, число итераций: %s \n",
                x, equantion.calculate(x), n);
    }
}
