package org.example.methods;

import org.example.equation.Equation;

public class RectangleMethod extends Method{
    public enum RectangleModification{
        RIGHT, LEFT, MIDDLE
    }
    RectangleModification modification;

    public RectangleMethod(Equation equantion, double accuracy) {
        super(equantion, accuracy);
    }

    public void setModification(RectangleModification modification) {
        this.modification = modification;
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

            switch (modification) {
                case RIGHT -> left += step;
                case LEFT -> right -= step;
                case MIDDLE -> {
                    left += step / 2;
                    right -= step / 2;
                }
            }

            previousSum = sum;
            sum = 0;
            for (double x = left; x <= right; x += step) {
                sum += equantion.calculate(x);
            }
            sum *= step * reverseMul;
        } while (getCurrentAccuracy() > accuracy);
        return sum;
    }
}

//x^4/4 + x |0 -2 = 0-(4-2) = -2
