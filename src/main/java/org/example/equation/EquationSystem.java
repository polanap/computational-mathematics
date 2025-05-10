package org.example.equation;

import java.util.Arrays;

public enum EquationSystem implements EquationSystemInterface {
    EQUATION1 ("1") {
        @Override
        public String toString(){
            return "x^2 + 2x + 1";
        }

        @Override
        public double[] calculate(double x, double y) {
            return new double[2];
        }
    },
    EQUATION2 ("2"){
        @Override
        public String toString(){
            return "x^3 + x^2 - 10x + 8";
        }

        @Override
        public double[] calculate(double x, double y) {
            return new double[2];
        }
    },
    EQUATION3 ("3"){
        @Override
        public String toString(){
            return "";
        }

        @Override
        public double[] calculate(double x, double y) {
            return new double[2];
        }
    };

    String id;

    EquationSystem(String id){
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

    public double [][] derivative(double x, double y) {
        double eps = 0.1e-10;
        double [][] derivatives = new double[2][2];
        derivatives[0][0] = (this.calculate(x, y)[0]-this.calculate(x-eps, y)[0])/eps;
        derivatives[0][1] = (this.calculate(x, y)[1]-this.calculate(x-eps, y)[1])/eps;
        derivatives[1][0] = (this.calculate(x, y)[0]-this.calculate(x, y-eps)[0])/eps;
        derivatives[1][1] = (this.calculate(x, y)[1]-this.calculate(x, y-eps)[1])/eps;
        return derivatives;
    }

}
