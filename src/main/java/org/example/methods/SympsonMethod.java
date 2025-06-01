package org.example.methods;

import org.example.equation.Equation;

public class SympsonMethod extends Method{
    public SympsonMethod(Equation equantion, double accuracy) {
        super(equantion, accuracy);
    }

    @Override
    public double calculate(double a, double b) throws Exception{
        int reverseMul = a<=b ? 1 : -1;
        int currentN = 0;
        n = 2;
        do {
            n*=2;
            if (currentN >= MAX_ITERATION_COUNT){
                throw new Exception("Не удалось посчитать интеграл с заданной точностью");
            }
            currentN++;

            double left = Math.min(a, b);
            double right = Math.max(a, b);
            double step = Math.abs(b - a) / n;

            previousSum = sum;
            sum = left+right;
            for (double x = left + step; x < right; x += step*2) {
                sum += 4 * equantion.calculate(x) + 2 * equantion.calculate(x + step);
            }
            sum *= step * reverseMul / 3;
        } while (getCurrentAccuracy() > accuracy);
        return sum;
    }

    @Override
    public double getCurrentAccuracy(){
        return Math.abs(sum - previousSum)/15;
    }
}