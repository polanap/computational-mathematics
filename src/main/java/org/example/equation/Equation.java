package org.example.equation;

import java.util.Arrays;

public enum Equation implements EquationInterface {
    EQUATION1 {
        @Override
        public String toString(){
            return "x^3 + 1 = 0";
        }

        @Override
        public double calculate(double x) {
            return x*x*x + 1;
        }
    },
    EQUATION2 {
        @Override
        public String toString(){
            return "x^3 + x^2 - 10x + 8 = 0";
        }

        @Override
        public double calculate(double x) {
            return x*x*x + x*x - 10*x + 8;
        }
    },
    EQUATION3 {
        @Override
        public String toString(){
            return "x^3 - 3,125x^2 - 3,5x + 2,458 = 0";
        }

        @Override
        public double calculate(double x) {
            return x*x*x - 3.125*x*x - 3.5*x +2.458;
        }
    },
    EQUATION4 {
        @Override
        public String toString(){
            return "0,1*cos(x)=0";
        }

        @Override
        public double calculate(double x) {
            return 0.1 * Math.cos(x);
        }
    };

    public static Equation getEquationByString(String value) {
        return Arrays.stream(Equation.values())
                .filter(it -> it.toString().equals(value))
                .findFirst()
                .orElseThrow();
    }


    public double derivative(double x) {
        double eps = 0.1e-10;
        return (this.calculate(x)-this.calculate(x-eps))/eps;
    }

}
