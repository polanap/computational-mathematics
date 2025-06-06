package org.example.approximation;

import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.stream.IntStream;

public class LinearApproximation extends Approximation {

    public LinearApproximation(double [] x, double [] y) {
        graphColor = Color.ORANGE;
        this.x = x;
        this.y = y;
        n = x.length;
        calculateCoefficients();
        createDeviation();
    }

    private void calculateCoefficients() {
        double sx = Arrays.stream(x).sum();
        double sy = Arrays.stream(y).sum();
        double sxx = Arrays.stream(x)
                .map(x->x*x)
                .sum();
        double sxy = IntStream.range(0, x.length)
                .mapToDouble(i->x[i]*y[i])
                .sum();
        double delta = sxx * n - sx * sx;
        double delta1 = sxy * n - sx * sy;
        double delta2 = sxx * sy - sx * sxy;
        a = delta1 / delta;
        b = delta2 / delta;
    }

    @Override
    public double calculate(double x) {
        return a*x + b;
    }

    public double PearsonCorrelationCoefficient(){
        return 0;
    }

    @Override
    public String toString() {
        return String.format("%s * x + %s", a, b);
    }
}
