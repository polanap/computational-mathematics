package org.example.task;

import static java.lang.Math.abs;

public class SlauTask {
    private int dimension;
    private double accuracy;
    private double [][] A;
    private double [] B;
    private double [][] C;
    private double [] D;
    double [] X;
    private double [] oldX;
    int k = 0;
    private int maxItter = 10000000;

    public SlauTask(double [][] A, double [] B, double accuracy) {
        dimension = A.length;
        this.A = A;
        this.B = B;
        this.accuracy = accuracy;
        C = new double[dimension][dimension];
        D = new double[dimension];
        X = new double[dimension];
        oldX = new double[dimension];
    }

    public void makeAnswer(){
        if (diagonalMajority()){
            System.out.println("Матрица является диагонально преобладающей, можно продолжить вычисление ответа");
        } else {
            System.out.println("Попытка привести матрицу к диагонально преобладающему виду...");
            makeDiagonalMajority();
            if (diagonalMajority()) {
                System.out.println("Теперь матрица является диагонально преобладающей, можно продолжить вычисление ответа");
                for (int i = 0; i < dimension; i++) {
                    for (int j = 0; j < dimension; j++) {
                        System.out.print(A[i][j] + " ");
                    }
                    System.out.println();
                }
            } else {
                System.out.println("Не получилось привести матрицу к диагонально преобладающему виду. Завершение вычислений");
                return;
            }
        }
        System.out.println(String.format("Норма мытрицы A: %.10f", norma(A)));
        try {
            makeCoeficients();
        } catch (Exception e) {
            System.out.println("Диагональные элементы не могу быть нулями");
            return;
        }
        try {
            k = calculate(k);
        } catch (Exception e) {
            System.out.println("Не удалось найти ответ с заданной точностью за максимальное число иттераций");
            return;
        }
        System.out.println(String.format("За %d иттераций был получен результат с необходимой точностью: ", k));
        for (double x : X) {
            System.out.print(String.format("%.10f ", x));
        }
        System.out.println();
        System.out.println("Вектор ошибок:");
        for (double e : errors()) {
            System.out.print(String.format("%.10f ", e));
        }
    }
    private int calculate(int k) throws Exception{
        if (k > maxItter) throw new Exception("Превышен лимит иттераций");
        if (isAccurate()) {
            return k;
        }
        oldX = X.clone();
        for (int i = 0; i < dimension; i++) {
            X[i] = D[i];
            for (int j = 0; j < dimension; j++) {
                X[i] += C[i][j] * X[j];
            }
        }
        return calculate(k+1);
    }

    private boolean diagonalMajority() {
        boolean isMajority = true;
        boolean isOneStrictlyGreater = false;
        for (int i = 0; i < dimension; i++) {
            double sum = 0;
            for (int j = 0; j < dimension; j++) {
                if (i == j) { continue; }
                sum += abs(A[i][j]);
            }
            isMajority &= abs(A[i][i]) >= sum;
            isOneStrictlyGreater |= abs(A[i][i]) > sum;
        }
        isMajority &= isOneStrictlyGreater;
        return isMajority;
    }

    private void makeDiagonalMajority() {
        for (int i = 0; i < dimension; i++) {
            double maxVal = A[i][0];
            int maxIndex = 0;
            for (int j = 0; j < dimension; j++) {
                if (A[i][j] > maxVal) {
                    maxVal = A[i][j];
                    maxIndex = j;
                }
            }
            if (maxIndex != i && maxVal != A[i][i]) {
                for (int j = 0; j < dimension; j++) {
                    double tmp = A[j][maxIndex];
                    A[j][maxIndex] = A[j][i];
                    A[j][i] = tmp;
                }

            }
        }
    }

    public void makeCoeficients() throws Exception {
        for (int i = 0; i < dimension; i++) {
            if (A[i][i] == 0) throw new Exception("На диагонали не могут быть нули");
            for (int j = 0; j < dimension; j++) {
                if (i != j){
                    C[i][j] = -A[i][j]/A[i][i];
                } else {
                    C[i][j] = 0;
                }
            }
            D[i] = B[i]/A[i][i];
        }
        X = D.clone();
    }

    public double [] distance (double [] X, double [] Y) throws Exception{
        if (X.length!= Y.length) throw new Exception("векторы, между которыми ищем расстояние, должны быть одной длины");
        double [] distance = new double[dimension];
        for (int i = 0; i < X.length; i++) {
            distance[i] = X[i] - Y[i];
        }
        return distance;
    }

    private boolean isAccurate(){
        double maxDiff = 0;
        for (double e : errors()){
            maxDiff = Math.max(maxDiff, Math.abs(e));
        }
        return (maxDiff <= accuracy);
    }

    public double norma (double [][] M){
        double sum = 0;
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[i].length; j++) {
                sum += M[i][j]*M[i][j];
            }
        }
        return Math.sqrt(sum);
    }

    private double [] errors (){
        double [] distance = new double[dimension];
        for (int i = 0; i < X.length; i++) {
            distance[i] = abs(X[i] - oldX[i]);
        }
        return distance;
    }


}
