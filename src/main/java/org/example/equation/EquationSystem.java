package org.example.equation;

import java.util.Arrays;

public enum EquationSystem implements EquationSystemInterface {
    EQUATION1 ("1") {
        @Override
        public String toString(){
            return "sin(x+1)-y=1,2 & 2x+cos(y)=2";
        }

        @Override
        public double[] calculate(double x, double y) {
            var ans = new double[2];
            ans[0] = Math.sin(x+1)-y-1.2;
            ans[1] = 2*x + Math.cos(y)-2;
            return ans;
        }
    },
    EQUATION2 ("2"){
        @Override
        public String toString(){
            return "sin(x+0,5)-y=1 & cos(y-2)+x=0";
        }

        @Override
        public double[] calculate(double x, double y) {
            var ans = new double[2];
            ans[0] = Math.sin(x+0.5)-y-1;
            ans[1] = Math.cos(y-2)+x;
            return ans;
        }
    },
    EQUATION3 ("3"){
        @Override
        public String toString(){
            return "sin(x+1)-2y=1,2 & x+cos(y)=2";
        }

        @Override
        public double[] calculate(double x, double y) {
            var ans = new double[2];
            ans[0] = Math.sin(x+1)-2*y-1.2;
            ans[1] = x + Math.cos(y)-2;
            return ans;
        }
    };

    String id;

    EquationSystem(String id){
        this.id = id;
    }

    public static EquationSystem getEquationById(String id) {
        return Arrays.stream(EquationSystem.values())
                .filter(it -> it.id.equals(id))
                .findFirst()
                .orElseThrow();
    }

    public static String getAllEquantions() {
        return String.join(
                "\n",
                Arrays.stream(EquationSystem.values())
                        .map(it -> it.id + " " + it)
                        .toList());
    }

    public double [][] derivative(double x, double y) {
        double eps = 0.1e-10;
        return new double[][]{
                {(this.calculate(x, y)[0]-this.calculate(x-eps, y)[0])/eps, (this.calculate(x, y)[0]-this.calculate(x, y-eps)[0])/eps},
                {(this.calculate(x, y)[1]-this.calculate(x-eps, y)[1])/eps, (this.calculate(x, y)[1]-this.calculate(x, y-eps)[1])/eps},
        };
    }

}
