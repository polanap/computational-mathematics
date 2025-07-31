package org.example.methods;

import org.example.functions.Function;

import static java.lang.Math.*;

public class MilnaMethod extends Method {

    public MilnaMethod(Function function, double start, double end, double y0, double h, double eps) {
        this.start = start;
        this.end = end;
        this.y0 = y0;
        this.function = function;
        this.h = h;
        this.eps = eps;
        x = makeXPoints(h);
        errors = new double[x.length];
        RungeKuttaMethod rkm = new RungeKuttaMethod(function, start, end, y0, h, eps); //todo: исправить -- если внутри метода шаг уменьшается -- результат неверный
        y = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            y[i] = rkm.y[i * ((rkm.x.length - 1) / (x.length - 1))];
        }
        calculate();
        calcFirstErrors();
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
                errors[i] = abs(function.calculate(x[i], x[0], y[0]) - y[i]);
            } while (errors[i] > eps && itterCount < maxItteration);

        }
    }

    public void calcFirstErrors() {
        for (int i = 1; i < min(x.length, 4); i++) {
            errors[i] = abs(function.calculate(x[i], x[0], y[0]) - y[i]);
        }
    }

    public double f(int i) {
        return function.derivative(x[i], y[i]);
    }
}
