package org.example.approximation;

import javafx.scene.paint.Color;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.stream.IntStream;

public class SecondDegreePolynomialApproximation extends Approximation {
    public SecondDegreePolynomialApproximation(double [] x, double [] y) {
        graphColor = Color.GREEN;
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
        double sx3 = Arrays.stream(x)
                .map(x->Math.pow(x, 3))
                .sum();
        double sx4 = Arrays.stream(x)
                .map(x->Math.pow(x, 4))
                .sum();
        double sxy = IntStream.range(0, n)
                .mapToDouble(i->x[i]*y[i])
                .sum();
        double sxxy = IntStream.range(0, n)
                .mapToDouble(i->x[i]*x[i]*y[i])
                .sum();
        double delta = n*sxx*sx4 + sx*sx3*sxx + sxx*sx*sx3 - sxx*sxx*sxx - sx3*sx3*n - sx4*sx*sx;
        double delta1 = sy*sxx*sx4 + sx*sx3*sxxy + sxx*sxy*sx3 - sxxy*sxx*sxx - sx3*sx3*sy - sx4*sxy*sx;
        double delta2 = n*sxy*sx4 + sy*sx3*sxx + sxx*sx*sxxy - sxx*sxy*sxx - sxxy*sx3*n - sx4*sx*sy;
        double delta3 = n*sxx*sxxy + sx*sxy*sxx + sy*sx*sx3 - sxx*sxx*sy - sx3*sxy*n - sxxy*sx*sx;
        c = delta1 / delta;
        b = delta2 / delta;
        a = delta3 / delta;
    }

    @Override
    public double calculate(double x) {
        return a*x*x + b*x + c;
    }

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("#.####");
        return String.format("%s * x^2 + %s * x + %s",
                decimalFormat.format(a), decimalFormat.format(b), decimalFormat.format(c));
    }

    @Override
    public String getStringAnswer(){
        return String.format("\nПолином второй степени: %s\n%s\n",
                toString(), super.getStringAnswer());
    }
}
