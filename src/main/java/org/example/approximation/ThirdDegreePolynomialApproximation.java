package org.example.approximation;

import javafx.scene.paint.Color;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.stream.IntStream;

public class ThirdDegreePolynomialApproximation extends Approximation {
    public ThirdDegreePolynomialApproximation(double [] x, double [] y) {
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
        double sx2 = Arrays.stream(x)
                .map(x->x*x)
                .sum();
        double sx3 = Arrays.stream(x)
                .map(x->Math.pow(x, 3))
                .sum();
        double sx4 = Arrays.stream(x)
                .map(x->Math.pow(x, 4))
                .sum();
        double sx5 = Arrays.stream(x)
                .map(x->Math.pow(x, 5))
                .sum();
        double sx6 = Arrays.stream(x)
                .map(x->Math.pow(x, 6))
                .sum();
        double sxy = IntStream.range(0, n)
                .mapToDouble(i->x[i]*y[i])
                .sum();
        double sx2y = IntStream.range(0, n)
                .mapToDouble(i->x[i]*x[i]*y[i])
                .sum();
        double sx3y = IntStream.range(0, n)
                .mapToDouble(i->x[i]*x[i]*x[i]*y[i])
                .sum();
        double delta = sx6*sx4*sx2*n + sx5*sx3*sx*sx3 + sx4*sx2*sx4*sx2 + sx3*sx5*sx3*sx - sx3*sx3*sx3*sx3 - sx2*sx2*sx2*sx6 - sx*sx*sx5*sx5 - n*sx4*sx4*sx4;
        double delta1 = sx3y*sx4*sx2*n + sx5*sx3*sx*sy + sx4*sx2*sxy*sx2 + sx3*sx2y*sx3*sx - sy*sx3*sx3*sx3 - sx2*sx2*sx2*sx3y - sx*sx*sx2y*sx5 - n*sxy*sx4*sx4;
        double delta2 = sx6*sx2y*sx2*n + sx3y*sx3*sx*sx3 + sx4*sx2*sx4*sy + sx3*sx5*sxy*sx - sx3*sxy*sx3*sx3 - sy*sx2*sx2*sx6 - sx*sx*sx5*sx3y - n*sx4*sx2y*sx4;
        double delta3 = sx6*sx4*sxy*n + sx5*sx2y*sx*sx3 + sx3y*sx2*sx4*sx2 + sx3*sx5*sx3*sy - sx3*sx3*sx2y*sx3 - sx2*sxy*sx2*sx6 - sy*sx*sx5*sx5 - n*sx4*sx4*sx3y;
        double delta4 = sx6*sx4*sx2*sy + sx5*sx3*sxy*sx3 + sx4*sx2y*sx4*sx2 + sx3y*sx5*sx3*sx - sx3*sx3*sx3*sx3y - sx2*sx2*sx2y*sx6 - sx*sxy*sx5*sx5 - sy*sx4*sx4*sx4;
        a = delta1 / delta;
        b = delta2 / delta;
        c = delta3 / delta;
        d = delta4 / delta;
    }

    @Override
    public double calculate(double x) {
        return a*x*x*x + b*x*x + c*x + d;
    }

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("#.####");
        return String.format("%s * x^3 + %s * x^2 + %s * x + %s",
                decimalFormat.format(a), decimalFormat.format(b), decimalFormat.format(c), decimalFormat.format(d));
    }

    @Override
    public String getStringAnswer(){
        return String.format("\nПолином третей степени: %s\n%s\n",
                toString(), super.getStringAnswer());
    }
}
