package org.example.methods;

import org.example.functions.Function;

public class EilerMethod extends Method {

    public EilerMethod(Function function, double[] x, double y0, double h, double eps) {
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
    protected void calculate() {
        for (int i = 1; i < count; i++) {
            y[i] = y[i - 1] + h * function.derivative(x[i - 1], y[i - 1]);
        }
    }
}
