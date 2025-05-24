package org.example.equation;

import java.util.Arrays;

public enum Equation implements EquationInterface {
    EQUATION1 ("1") {
        @Override
        public String toString(){
            return "x^2 + 2x + 1 = 0";
        }

        @Override
        public double calculate(double x) {
            return x*x + 2*x + 1;
        }
    },
    EQUATION2 ("2"){
        @Override
        public String toString(){
            return "x^3 + x^2 - 10x + 8 = 0";
        }

        @Override
        public double calculate(double x) {
            return x*x*x + x*x - 10*x + 8;
        }
    },
    EQUATION3 ("3"){
        @Override
        public String toString(){
            return "x^3 - 3,125x^2 - 3,5x + 2,458 = 0";
        }

        @Override
        public double calculate(double x) {
            return x*x*x - 3.125*x*x - 3.5*x +2.458;
        }
    };

    String id;

    Equation(String id){
        this.id = id;
    }

    public static Equation getEquationById(String id) {
        return Arrays.stream(Equation.values())
                .filter(it -> it.id.equals(id))
                .findFirst()
                .orElseThrow();
    }

    public static String getAllEquantions() {
        return String.join(
                "\n",
                Arrays.stream(Equation.values())
                .map(it -> it.id + " " + it.toString())
                .toList());
    }

    public double derivative(double x) {
        double eps = 0.1e-10;
        return (this.calculate(x)-this.calculate(x-eps))/eps;
    }

}
