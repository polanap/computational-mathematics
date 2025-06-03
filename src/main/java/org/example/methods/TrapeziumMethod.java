package org.example.methods;

import org.example.equation.Equation;

public class TrapeziumMethod extends Method{
    public TrapeziumMethod(Equation equantion, double accuracy) {
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
            sum = (left+right)/2;
            for (double x = left + step; x < right; x += step) {
                sum += equantion.calculate(x);
            }
            sum *= step * reverseMul;
        } while (getCurrentAccuracy() > accuracy);
        return sum;
    }
    public double runge(){
        return Math.abs(sum - previousSum)/3;
    }
}