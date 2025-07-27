package org.example.interpolation;

import javafx.scene.paint.Color;

public class NewtoneDevidedInterpolation extends Interpolation {
    double[][] div;

    public NewtoneDevidedInterpolation(double[] x, double[] y) {
        graphColor = Color.ORANGE;
        this.x = x;
        this.y = y;
        n = x.length;
        div = new DividedDiviation(x, y).div;
    }

    @Override
    public double calculate(double a) {
        double result = div[0][0];
        for (int k = 1; k < n; k++) {
            double tmpResult = div[k][0];
            for (int j = 0; j < k; j++) {
                tmpResult *= (a - x[j]);
            }
            result += tmpResult;
        }
        return result;
    }
}
