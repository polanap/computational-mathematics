package org.example.methods;

import org.example.functions.Function;

public class RungeKuttaMethod extends Method {

    public RungeKuttaMethod(Function function, double[] x, double y0, double h, double eps) {
        this.x = x;
        count = x.length;
        y = new double[count];
        y[0] = y0;
        this.function = function;
        this.h = h;
        this.eps = eps;
        calculate();
    }

    @Override
    public void calculate() {
        for (int i = 1; i < count; i++) {
            double k1 = h * function.derivative(x[i - 1], y[i - 1]);
            double k2 = h * function.derivative(x[i - 1] + h / 2, y[i - 1] + k1 / 2);
            double k3 = h * function.derivative(x[i - 1] + h / 2, y[i - 1] + k2 / 2);
            double k4 = h * function.derivative(x[i - 1] + h, y[i - 1] + k3);
            y[i] = y[i - 1] + (k1 + 2 * k2 + 2 * k3 + k4) / 6;
        }
    }

}
