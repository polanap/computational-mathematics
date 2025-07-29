package org.example.functions;

import java.util.Arrays;

public enum Function implements FunctionInterface {
    FUNCTION_1 {
        @Override
        public String toString() {
            return "sin(x)";
        }

        @Override
        public double calculate(double x) {
            return Math.sin(x);
        }
    },
    FUNCTION_2 {
        @Override
        public String toString() {
            return "cos(x)*0.5 + 1";
        }

        @Override
        public double calculate(double x) {
            return Math.cos(x) * 0.5 + 1;
        }
    },
    FUNCTION_3 {
        @Override
        public String toString() {
            return "x^3 - x^2 - x + 2 = 0";
        }

        @Override
        public double calculate(double x) {
            return x * x * x - x * x - x + 2;
        }
    };


    public static String getAllFunctions() {
        return String.join(
                "\n",
                Arrays.stream(Function.values())
                        .map(Enum::toString)
                        .toList());
    }

    public double derivative(double x) {
        double eps = 0.1e-10;
        return (this.calculate(x) - this.calculate(x - eps)) / eps;
    }

}
