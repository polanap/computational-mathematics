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

    public SystemMethod(EquationSystem equantion, double accuracy) {
        this.equantion = equantion;
        this.accuracy = accuracy;
    }

    @Override
    public double[] calculate(double x, double y) {
        var system = new double[2];
        return system;
    }
}

