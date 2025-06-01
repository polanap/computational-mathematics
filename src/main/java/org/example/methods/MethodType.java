package org.example.methods;

import org.example.equation.Equation;

import java.util.Arrays;

public enum MethodType {
    RECTANGLE_LEFT{
        @Override
        public String toString(){
            return "Метод прямоугольников (левые прямогугольники)";
        }
    },
    RECTANGLE_RIGHT{
        @Override
        public String toString(){
            return "Метод прямоугольников (правые прямогугольники)";
        }
    },
    RECTANGLE_MIDDLE{
        @Override
        public String toString(){
            return "Метод прямоугольников (средние прямогугольники)";
        }
    },
    TRAPEZIUM{
        @Override
        public String toString(){
            return "Метод трапеций";
        }
    },
    SIMPSON{
        @Override
        public String toString(){
            return "Метод Симпсона";
        }
    };

    public static MethodType getMethodByString(String value) {
        return Arrays.stream(MethodType.values())
                .filter(it -> it.toString().equals(value))
                .findFirst()
                .orElseThrow();
    }
}
