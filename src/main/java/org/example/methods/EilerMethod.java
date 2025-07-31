package org.example.methods;

import org.example.functions.Function;

import java.util.Arrays;

public class EilerMethod extends Method {

    public EilerMethod(Function function, double start, double end, double y0, double h, double eps) {
        this.start = start;
        this.end = end;
        this.y0 = y0;
        this.function = function;
        this.h = h;
        this.eps = eps;
        calculate();
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
            x2h = makeXPoints(h);
            y2h = calculateWithFixH(h, x2h);
            xh = makeXPoints(h / 2);
            yh = calculateWithFixH(h / 2, xh);
            errors = runge(y2h, yh);
            h/=2;
        } while (Arrays.stream(errors).max().getAsDouble() > eps && currentItteration < maxItteration);
        y = y2h;
        x = x2h;
    }

    protected double[] calculateWithFixH(double h, double[] x) {
        int count = getCount(h);
        double[] yh = new double[count];
        for (int i = 1; i < count; i++) {
            yh[i] = yh[i - 1] + h * function.derivative(x[i - 1], yh[i - 1]);
        }
        return yh;
    }
}
