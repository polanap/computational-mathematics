package org.example.methods;

import org.example.equation.Equation;

public class ChordMethod extends Method {

    public ChordMethod(Equation equantion, double accuracy) {
        super(equantion, accuracy);
    }

    @Override
    public double calculate(double a, double b) throws Exception {
        if (n >= MAX_ITERATION_COUNT){
            throw new Exception("Не удалось найти решение за заданное количество итераций");
        }
        x = a - (b - a)*equantion.calculate(a)/ (equantion.calculate(b) - equantion.calculate(a));
        if (Math.abs(equantion.calculate(x)) <= accuracy) {
            return x;
        }
        n += 1;
        if (equantion.calculate(x) * equantion.calculate(a) <= 0) {
            return calculate(a, x);
        } else {
            return calculate(x, b);
        }
    }
}
