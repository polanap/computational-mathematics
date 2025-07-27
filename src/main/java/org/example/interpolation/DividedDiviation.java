package org.example.interpolation;

public class DividedDiviation {
    int n;
    double[] y;
    double[][] div;

    public DividedDiviation(double[] y) {
        this.y = y;
        n = y.length;
        div = new double[n][n];
        calculateDiviation();
    }

    private void calculateDiviation() {
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n - k; i++) {
                if (k == 0) div[k][i] = y[i];
                else div[k][i] = div[k - 1][i + 1] - div[k-1][i];
            }
        }
    }
}
