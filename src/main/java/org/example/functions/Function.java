package org.example.functions;

import java.util.Arrays;
import java.util.List;

import static java.lang.Math.exp;

public enum Function implements FunctionInterface {
    FUNCTION_1 {
        @Override
        public String toString() {
            return "y' = y + (1 + x)y^2";
        }

        @Override
        public double derivative(double x, double y) {
            return y + (1 + x) * y * y;
        }

        @Override
        public double calculate(double x, double x0, double y0) {
            double c = y0 / (exp(x0) * (y0 * x0 + y0 + 1));
            return (c * exp(x)) / (1 - c * exp(x) * (1 + x));
        }

    },
    FUNCTION_2 {
        @Override
        public String toString() {
            return "y' = 2xy / (x^2 + 1)";
        }

        @Override
        public double calculate(double x, double x0, double y0) {
            double c = y0 / (x0 * x0 + 1);
            return c * (x * x + 1);
        }

        @Override
        public double derivative(double x, double y) {
            return 2 * x * y / (x * x + 1);
        }
    },
    FUNCTION_3 {
        @Override
        public String toString() {
            return "y' = y + 1 + x";
        }

        @Override
        public double calculate(double x, double x0, double y0) {
            double c = (y0 + x0 + 2) / exp(x0);
            return -x - 2 + c * exp(x);
        }

        @Override
        public double derivative(double x, double y) {
            return y + 1 + x;
        }
    };


    public static List<String> getAllFunctions() {
        return Arrays.stream(Function.values())
                .map(Enum::toString)
                .toList();
    }

    public static Function getFunctionByStr(String fun) {
        return Arrays.stream(Function.values())
                .filter(it -> it.toString().equals(fun))
                .findFirst()
                .orElseThrow();
    }

}
