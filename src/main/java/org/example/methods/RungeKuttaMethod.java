package org.example.methods;

import org.example.functions.Function;

import java.util.Arrays;

public class RungeKuttaMethod extends Method {

    public RungeKuttaMethod(Function function, double start, double end, double y0, double h, double eps) {
        this.start = start;
        this.end = end;
        this.y0 = y0;
        this.function = function;
        this.h = h;
        this.currentH = h;
        this.eps = eps;
        x = makeXPoints(h);
        y = new double[x.length];
        calculate();
        normalise();
    }


    @Override
    protected void calculate() {
        int currentItteration = 0;
        double[] y2h;
        double[] yh;
        double[] x2h;
        double[] xh;
        do {
            currentItteration++;
            x2h = makeXPoints(currentH);
            y2h = calculateWithFixH(currentH, x2h);
            xh = makeXPoints(currentH / 2);
            yh = calculateWithFixH(currentH / 2, xh);
            errors = runge(y2h, yh);
            currentH /= 2;
        } while (Arrays.stream(errors).max().getAsDouble() > eps && currentItteration < MAX_ITTERATIONS);
        currentH *= 2;
        y = y2h;
        x = x2h;
    }

    protected double[] calculateWithFixH(double h, double[] x) {
        int count = getCount(h);
        double[] y = new double[count];
        for (int i = 1; i < x.length; i++) {
            double k1 = h * function.derivative(x[i - 1], y[i - 1]);
            double k2 = h * function.derivative(x[i - 1] + h / 2, y[i - 1] + k1 / 2);
            double k3 = h * function.derivative(x[i - 1] + h / 2, y[i - 1] + k2 / 2);
            double k4 = h * function.derivative(x[i - 1] + h, y[i - 1] + k3);
            y[i] = y[i - 1] + (k1 + 2 * k2 + 2 * k3 + k4) / 6;
        }
        return y;
    }

}
