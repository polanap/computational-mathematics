package org.example.methods.system;

import org.example.equation.EquationSystem;

public class NewtonMethod extends SystemMethod {
    public NewtonMethod(EquationSystem equantion, double accuracy) {
        super(equantion, accuracy);
    }

    @Override
    public double[] calculate(double x0, double y0) throws Exception{
        double x = x0;
        double y = y0;

        for (int i = 0; i < maxN; i++) {
            double[][] J = equantion.derivative(x, y);
            double f1 = equantion.calculate(x, y)[0];
            double f2 = equantion.calculate(x, y)[1];

            double deltaX = -(J[0][0] * f1 + J[0][1] * f2) / (J[0][0] * J[1][1] - J[0][1] * J[1][0]);
            double deltaY = -(J[1][0] * f1 + J[1][1] * f2) / (J[0][0] * J[1][1] - J[0][1] * J[1][0]);

            x += deltaX;
            y += deltaY;

            if (Math.abs(deltaX) < accuracy && Math.abs(deltaY) < accuracy) {
                return new double[]{x, y};
            }
        }
        throw new Exception("Не удалось найти решение за заданное количество итераций");
    }
}
