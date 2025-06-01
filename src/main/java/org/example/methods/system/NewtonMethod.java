package org.example.methods.system;

import org.example.equation.EquationSystem;

public class NewtonMethod extends SystemMethod {
    public NewtonMethod(EquationSystem equantion, double accuracy) {
        super(equantion, accuracy);
    }

    @Override
    public double[] calculate(double x0, double y0) throws Exception {
        x = x0;
        y = y0;

        for (int i = 0; i < maxN; i++) {
            n = i;
            double[][] J = equantion.derivative(x, y);
            double f1 = equantion.calculate(x, y)[0];
            double f2 = equantion.calculate(x, y)[1];

            double determinant = J[0][0] * J[1][1] - J[0][1] * J[1][0];
            if (Math.abs(determinant) < 1e-10) {
                throw new Exception("Матрица Якоби вырождена, метод Ньютона не может быть применен.");
            }

            deltaX = (J[0][1] * f2 - J[1][1] * f1) / determinant;
            deltaY = (J[1][0] * f1 - J[0][0] * f2) / determinant;

            x += deltaX;
            y += deltaY;

            if (Math.abs(deltaX) < accuracy && Math.abs(deltaY) < accuracy) {
                return new double[]{x, y};
            }
        }
        throw new Exception("Не удалось найти решение за заданное количество итераций");
    }
}
