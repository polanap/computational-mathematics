package org.example.interpolation.diviation;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FinalDiviation {
    int n;
    double[] y;
    public double[][] div;

    public FinalDiviation(double[] y) {
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
