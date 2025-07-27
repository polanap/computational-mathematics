package org.example.interpolation;

import javafx.scene.paint.Color;

public class NewtoneDevidedInterpolation extends Interpolation {
    double[][] div;

    public NewtoneDevidedInterpolation(double [] x, double [] y) {
        graphColor = Color.ORANGE;
        this.x = x;
        this.y = y;
        n = x.length;
        div = new FinalDiviation(y).div;
    }

    @Override
    double calculate(double x) {
        return 0;
    }
}
