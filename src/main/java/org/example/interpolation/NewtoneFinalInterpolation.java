package org.example.interpolation;

import javafx.scene.paint.Color;

public class NewtoneFinalInterpolation extends Interpolation {
    double[][] div;
    double h;

    public NewtoneFinalInterpolation(double[] x, double[] y) throws Exception {
        h = x[1] - x[0];
        for (int i = 2; i < x.length; i++) {
            if (h != x[i] - x[i - 1])
                throw new Exception("Узлы не являются равноотстоящими. Невозможно посчитать результат.");
        }
        graphColor = Color.ORANGE;
        this.x = x;
        this.y = y;
        n = x.length;
        div = new FinalDiviation(y).div;
    }

    @Override
    public double calculate(double a) {
        if (a <= (x[n - 1] + x[0]) / 2) return calculate1(a);
        else return calculate2(a);
    }

    double calculate1(double a) {
        double t = (a - x[0]) / h;
        double result = 0;
        double coefficient = 1;
        double factorial = 1;
        for (int k = 0; k < n; k++) {
            result += coefficient * div[k][0] / factorial;
            coefficient *= (t - k);
            factorial *= (k + 1);
        }
        return result;
    }

    double calculate2(double a) {
        double t = (a - x[n-1]) / h;
        double result = 0;
        double coefficient = 1;
        double factorial = 1;
        for (int k = 0; k < n; k++) {
            result += coefficient * div[k][n - k - 1] / factorial;
            coefficient *= (t + k);
            factorial *= (k + 1);
        }
        return result;
    }
}
