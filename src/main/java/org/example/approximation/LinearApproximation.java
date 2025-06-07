package org.example.approximation;

import javafx.scene.paint.Color;

import java.text.DecimalFormat;
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
        double sxy = IntStream.range(0, n)
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

    public double pearsonCorrelationCoefficient(){
        double midX = Arrays.stream(x).sum()/n;
        double midY = Arrays.stream(y).sum()/n;
        double a = IntStream.range(0, n).mapToDouble(i->(x[i]-midX)*(y[i]-midY)).sum();
        double b = IntStream.range(0, n).mapToDouble(i->(x[i]-midX)*(x[i]-midX)).sum();
        double c = IntStream.range(0, n).mapToDouble(i->(y[i]-midY)*(y[i]-midY)).sum();
        return a/Math.sqrt(b*c);
    }

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("#.####");
        return String.format("%s * x + %s", decimalFormat.format(a), decimalFormat.format(b));
    }

    @Override
    public String getStringAnswer(){
        return String.format("\nЛинейная аппроксимирующая функция: %s\n%sКоэффициент корелляции Пирсона: %s\n",
                toString(), super.getStringAnswer(), pearsonCorrelationCoefficient());
    }
}
