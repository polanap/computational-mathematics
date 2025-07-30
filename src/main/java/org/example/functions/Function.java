package org.example.functions;

import java.util.Arrays;
import java.util.List;

public enum Function implements FunctionInterface {
    FUNCTION_1 {
        @Override
        public String toString() {
            return "y' = y + (1 + x)y^2";
        }

        @Override
        public double derivative(double x, double y) {
            return 0;
        }

        @Override
        public double derivativeExact(double x, double y) {
            return 0;
        }
    },
    FUNCTION_2 {
        @Override
        public String toString() {
            return "y' = 2xy / (x^2 + 1)";
        }

        @Override
        public double derivative(double x, double y) {
            return 0;
        }

        @Override
        public double derivativeExact(double x, double y) {
            return 0;
        }
    },
    FUNCTION_3 {
        @Override
        public String toString() {
            return "y' = y + 1 + x";
        }

        @Override
        public double derivative(double x, double y) {
            return 0;
        }

        @Override
        public double derivativeExact(double x, double y) {
            return 0;
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
