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
        double [][] A = {
                {sx6, sx5, sx4, sx3},
                {sx5, sx4, sx3, sx2},
                {sx4, sx3, sx2, sx},
                {sx3, sx2, sx, n}
        };
        double [] B = {sx3y, sx2y, sxy, sy};
        double det = det4degree(A);
        double det1 = det4degree(new double[][]{
                {B[0], A[0][1], A[0][2], A[0][3]},
                {B[1], A[1][1], A[1][2], A[1][3]},
                {B[2], A[2][1], A[2][2], A[2][3]},
                {B[3], A[3][1], A[3][2], A[3][3]}
        });
        double det2 = det4degree(new double[][]{
                {A[0][0], B[0], A[0][2], A[0][3]},
                {A[1][0], B[1], A[1][2], A[1][3]},
                {A[2][0], B[2], A[2][2], A[2][3]},
                {A[3][0], B[3], A[3][2], A[3][3]}
        });
        double det3 = det4degree(new double[][]{
                {A[0][0], A[0][1], B[0], A[0][3]},
                {A[1][0], A[1][1], B[1], A[1][3]},
                {A[2][0], A[2][1], B[2], A[2][3]},
                {A[3][0], A[3][1], B[3], A[3][3]}
        });
        double det4 = det4degree(new double[][]{
                {A[0][0], A[0][1], A[0][2], B[0]},
                {A[1][0], A[1][1], A[1][2], B[1]},
                {A[2][0], A[2][1], A[2][2], B[2]},
                {A[3][0], A[3][1], A[3][2], B[3]}
        });
        a = det1 / det;
        b = det2 / det;
        c = det3 / det;
        d = det4 / det;
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
