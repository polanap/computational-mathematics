package org.example.methods;

import org.example.equation.Equation;

public class SimpleIterationMethod extends Method {
    double lambda;
    double q = 0;
    public SimpleIterationMethod(Equation equantion, double accuracy) {
        super(equantion, accuracy);
        x = 0;
        oldX = 0;

    }

    @Override
    public double calculate(double a, double b) throws Exception {
        if (n >= MAX_ITERATION_COUNT){
            throw new Exception("Не удалось найти решение за заданное количество итераций");
        }
        if (n == 0) {
            q = getConvergesCoefficient(a, b);
            lambda = getLambda(a, b);
        }
        n += 1;
        oldX = x;
        x = x + lambda * equantion.calculate(x);
        if (Math.abs(x - oldX) <= accuracy) {
            return x;
        }
        return calculate(a, x);
    }

    private double getLambda(double a, double b) {
        double current = a;
        double maxDerivative = 0;
        while (current <= b) {
            maxDerivative = Math.max(maxDerivative, Math.abs(equantion.derivative(current)));
            current += accuracy;
        }
        return 1/maxDerivative;
    }

    private double getConvergesCoefficient(double a, double b) {
        double current = a;
        double maxCoefficient = 0;
        while (current <= b) {
            maxCoefficient = Math.max(maxCoefficient, Math.abs(equantion.derivative(current)));
            current += accuracy;
        }
        return maxCoefficient;
    }

    public boolean isConverged(double a, double b) {
//        System.out.println(getConvergesCoefficient(a, b));
        return getConvergesCoefficient(a, b) < 1;
    }
}
