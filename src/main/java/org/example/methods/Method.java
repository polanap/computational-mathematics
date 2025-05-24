package org.example.methods;

import org.example.equation.Equation;

public class Method implements Calculatable {
    Equation equantion;
    double accuracy;
    double x = 0;
    double oldX = 0;
    int n = 0;

    public Method(Equation equantion, double accuracy) {
        this.equantion = equantion;
        this.accuracy = accuracy;
    }

    @Override
    public double calculate(double a, double b) {
        return 0;
    }

    public void printResult(){
        System.out.println(String.format("Значение корня: %s, Значение функции в корне: %s, число итераций: %s",
                x, equantion.calculate(x), n));
    }
}
