package org.example.interpolation;

import javafx.scene.paint.Color;

public class NewtoneInterpolation extends Interpolation {

    public NewtoneInterpolation(double [] x, double [] y) {
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
