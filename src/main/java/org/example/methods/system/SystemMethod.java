package org.example.methods.system;

import org.example.equation.Equation;
import org.example.equation.EquationSystem;
import org.example.methods.Calculatable;

public class SystemMethod implements CalculatableSystem {
    EquationSystem equantion;
    double accuracy;
    double x = 0;
    double y = 0;
    double deltaX = 0;
    double deltaY = 0;
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

    public String printResult(){
        var function = equantion.calculate(x, y);
        return String.format("Решение системы: (%s, %s), \nЗначение функции в корне: (%s, %s), \nчисло итераций: %s, \nвектор погрешностей: (%s, %s)\n",
                x, y, function[0], function[1], n, deltaX, deltaY );
    }
}

