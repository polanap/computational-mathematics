package org.example.methods;

import org.example.functions.Function;

public class MilnaMethod extends Method {

    public MilnaMethod(Function function, double[] x, double y0, double h, double eps) {
        this.x = x;
        count = x.length;
        y = new double[count];
        y[0] = y0;
        this.function = function;
        this.h = h;
        this.eps = eps;
    }

    @Override
    public void calculate() {
        for (int i = 3; i < count; i++) {
            double yPredict = y[i - 4] + 4 * h * (2 * f(i - 3) - f(i - 2) + 2 * f(i - 1)) / 3;
            double fPredict = function.derivative(x[i], yPredict);
            y[i] = y[i - 2] + h * (f(i - 2) + 4 * f(i - 1) + fPredict) / 3;
        }
    }

    public double f(int i) {
        return function.derivative(x[i], y[i]);
    }
}
