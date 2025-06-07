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
        double det = n*sxx*sx4 + sx*sx3*sxx + sxx*sx*sx3 - sxx*sxx*sxx - sx3*sx3*n - sx4*sx*sx;
        double det1 = sy*sxx*sx4 + sx*sx3*sxxy + sxx*sxy*sx3 - sxxy*sxx*sxx - sx3*sx3*sy - sx4*sxy*sx;
        double det2 = n*sxy*sx4 + sy*sx3*sxx + sxx*sx*sxxy - sxx*sxy*sxx - sxxy*sx3*n - sx4*sx*sy;
        double det3 = n*sxx*sxxy + sx*sxy*sxx + sy*sx*sx3 - sxx*sxx*sy - sx3*sxy*n - sxxy*sx*sx;
        c = det1 / det;
        b = det2 / det;
        a = det3 / det;
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

//0.000, 0.400, 0.800, 1.200, 1.600, 2.000, 2.400, 2.800, 3.200, 3.600, 4.000
//0.000, 0.718, 1.383, 1.789, 1.740, 1.385, 1.001, 0.705, 0.501, 0.364, 0.271