package org.example.methods;

import org.example.functions.Function;

import static java.lang.Math.*;

/**
 * Метод Милна
 */
public class MilnaMethod extends Method {

    int count;

    public MilnaMethod(Function function, double start, double end, double y0, double h, double eps) throws Exception {
        this.start = start;
        this.end = end;
        this.y0 = y0;
        this.function = function;
        this.h = h;
        this.currentH = h;
        this.eps = eps;
        x = makeXPoints(h);
        count = x.length;
        errors = new double[count];
        RungeKuttaMethod rkm = new RungeKuttaMethod(function, start, end, y0, h, eps);
        y = new double[count];
        for (int i = 0; i < count; i++) {
            y[i] = rkm.y[i * ((rkm.x.length - 1) / (count - 1))];
        }
        calculate();
        calcFirstErrors();
        normalise();
    }

    @Override
    public void calculate() {
        for (int i = 4; i < count; i++) {
            double itterCount = 0;
            double yPredict;
            y[i] = y[i - 4] + 4 * h * (2 * f(i - 3) - f(i - 2) + 2 * f(i - 1)) / 3;
            do {
                yPredict = y[i];
                double fPredict = function.derivative(x[i], yPredict);
                y[i] = y[i - 2] + h * (f(i - 2) + 4 * f(i - 1) + fPredict) / 3;
                itterCount++;
                errors[i] = abs(function.calculate(x[i], x[0], y[0]) - y[i]);
            } while (errors[i] > eps && itterCount < MAX_ITTERATIONS);

        }
    }

    public void calcFirstErrors() {
        for (int i = 1; i < min(count, 4); i++) {
            errors[i] = abs(function.calculate(x[i], x[0], y[0]) - y[i]);
        }
    }

    public double f(int i) {
        return function.derivative(x[i], y[i]);
    }
}
