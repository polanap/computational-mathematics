package org.example.methods;

import org.example.functions.Function;

import static java.lang.Math.abs;

public class MilnaMethod extends Method {

    public MilnaMethod(Function function, double start, double end, double y0, double h, double eps) {
        this.start = start;
        this.end = end;
        this.y0 = y0;
        this.function = function;
        this.h = h;
        this.eps = eps;
        x = makeXPoints(h);
        RungeKuttaMethod rkm = new RungeKuttaMethod(function, start, end, y0, h, eps);
        y = rkm.y;
        calculate();
    }

    @Override
    public void calculate() {
        for (int i = 4; i < x.length; i++) {
            double itterCount = 0;
            double yPredict;
            y[i] = y[i - 4] + 4 * h * (2 * f(i - 3) - f(i - 2) + 2 * f(i - 1)) / 3;
            do {
                yPredict = y[i];
                double fPredict = function.derivative(x[i], yPredict);
                y[i] = y[i - 2] + h * (f(i - 2) + 4 * f(i - 1) + fPredict) / 3;
                itterCount++;
            } while (abs(y[i] - yPredict) > eps && itterCount < maxItteration);

        }
    }

    public double f(int i) {
        return function.derivative(x[i], y[i]);
    }
}
