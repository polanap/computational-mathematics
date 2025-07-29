package org.example.functions;

import java.util.Arrays;
import java.util.List;

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
