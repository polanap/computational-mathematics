package org.example.interpolation;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class FinalDiviation {
    int n;
    double[] y;
    double[][] div;

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

    public String getStringTable() {

        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow(makeTitleRow(n));
        asciiTable.addRule();
        for (int k = 0; k < n; k++) {
            asciiTable.addRow(makeRow("k = " + k, div[k], k));
            asciiTable.addRule();
        }

        asciiTable.setTextAlignment(TextAlignment.CENTER);
        return asciiTable.render() + "\n";

    }

    public static List<Object> makeTitleRow(int n) {
        List<Object> list = new ArrayList<>(n);
        list.add("");
        for (int i = 0; i < n; i++) {
            list.add(String.format("i = %d", i));
        }
        return list;
    }


    public static List<Object> makeRow(String title, double[] array, int i) {
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        if (array == null) {
            return new ArrayList<>(0);
        } else {
            int size = array.length;
            List<Object> list = new ArrayList<>(size);
            list.add(title);
            for (int k = 0; k < size; k++) {
                if (k >= size - i) list.add("-");
                else list.add(decimalFormat.format(array[k]));
            }
            return list;
        }
    }
}
