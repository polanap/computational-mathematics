package org.example.interpolation;

import javafx.scene.paint.Color;

public class LagrangeInterpolation extends Interpolation {

    public LagrangeInterpolation(double[] x, double[] y) {
        graphColor = Color.ORANGE;
        this.x = x;
        this.y = y;
        n = x.length;
    }

    @Override
    public double calculate(double a) {
        double result = 0;
        for (int i = 0; i < n; i++) {
            double tmpResult = y[i];
            for (int j = 0; j < n; j++) {
                if (i != j) tmpResult *= (a - x[j])/(x[i] - x[j]);
            }
            result += tmpResult;
        }
        return result;
    }
}
