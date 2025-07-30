package org.example.functions;

public interface FunctionInterface {
    /**
     * Левая часть уравнения y' = f(x,y)
     * @param x
     * @param y
     * @return
     */
    public double derivative(double x, double y);

    /**
     * Точное решение ОДУ
     * @param x
     * @param y0 - начальное условие y
     * @param x0 - начальное условие x
     * @return
     */
    public double calculate(double x, double x0, double y0);
}
