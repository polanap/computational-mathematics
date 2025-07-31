package org.example.methods;

import org.example.functions.Function;

import java.util.Arrays;

/**
 * Метод Эйлера
 */
public class EilerMethod extends Method {

    public EilerMethod(Function function, double start, double end, double y0, double h, double eps) throws Exception {
        this.start = start;
        this.end = end;
        this.y0 = y0;
        this.function = function;
        this.h = h;
        this.currentH = h;
        this.eps = eps;
        calculate();
        normalise();
    }

    @Override
    protected void calculate() throws Exception {
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
            currentH /=2;
        } while (Arrays.stream(errors).max().getAsDouble() > eps && currentItteration < MAX_ITTERATIONS);
        currentH *= 2;
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
