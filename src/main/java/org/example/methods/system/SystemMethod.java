package org.example.methods.system;

import org.example.equation.Equation;
import org.example.equation.EquationSystem;
import org.example.methods.Calculatable;

public class SystemMethod implements CalculatableSystem {
    EquationSystem equantion;
    double accuracy;
    double x = 0;
    double y = 0;
    double oldX = 0;
    double oldY = 0;
    int n = 0;
    int maxN = 100000;
    public SystemMethod(EquationSystem equantion, double accuracy) {
        this.equantion = equantion;
        this.accuracy = accuracy;
    }

    @Override
    public double[] calculate(double x, double y) throws Exception {
        var system = new double[2];
        return system;
    }

    public void printResult(){
        var function = equantion.calculate(x, y);
        System.out.println(String.format("Решение системы: (%s, %s), Значение функции в корне: (%s, %s), число итераций: %s, вектор погрешностей: (%s, %s)",
                x, y, function[0], function[1], n, x-oldX, y-oldY ));
    }
}

