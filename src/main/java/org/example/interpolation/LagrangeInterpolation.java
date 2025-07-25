package org.example.interpolation;

import javafx.scene.paint.Color;

public class LagrangeInterpolation extends Interpolation {

    public LagrangeInterpolation(double [] x, double [] y) {
        graphColor = Color.ORANGE;
        this.x = x;
        this.y = y;
        n = x.length;
//        calculateCoefficients();
//        createDeviation();
    }

    @Override
    double calculate(double x) {
        return 0;
    }
}
