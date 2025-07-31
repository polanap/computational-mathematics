package org.example.functions;

import java.util.Arrays;
import java.util.List;

import static java.lang.Math.*;

public enum Function implements FunctionInterface {
    FUNCTION_1 {
        @Override
        public String toString() {
            return "y' = sin(x) - y";
        }

        @Override
        public double derivative(double x, double y) {
            return sin(x) - y;
        }

        @Override
        public double calculate(double x, double x0, double y0) {
            double c = (2*exp(x0)* y0-exp(x0)* sin(x0)+exp(x0)* cos(x0));
            return c / (2*exp(x)) + (sin(x)) / 2 - (cos(x)) / 2;
        }

    },
    FUNCTION_2 {
        @Override
        public String toString() {
            return "y' = e^x";
        }

        @Override
        public double calculate(double x, double x0, double y0) {
            double c = y0 - exp(x0);
            return  c + exp(x);
        }

        @Override
        public double derivative(double x, double y) {
            return exp(x);
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
