package org.example.methods;

import org.example.equation.Equation;

public class SecantMethod extends Method {
    double prevOldx;
    public SecantMethod(Equation equantion, double accuracy) {
        super(equantion, accuracy);
    }

    @Override
    public double calculate(double a, double b) throws Exception{
        if (n >= MAX_ITERATION_COUNT){
            throw new Exception("Не удалось найти решение за заданное количество итераций");
        }
        if (n < 2) {
            oldX = b;
            x = a;
        }
        prevOldx = oldX;
        oldX = x;
        if (Math.abs(equantion.calculate(x)) <= accuracy) {
            return x;
        }
        n += 1;
        x = oldX - (oldX - prevOldx)*equantion.calculate(oldX)/ (equantion.calculate(oldX) - equantion.calculate(prevOldx));
        return calculate(a, b);
    }
}
