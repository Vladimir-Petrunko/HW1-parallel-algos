package org.example;

public class Estimator {
    public static double log2(int p) {
        return Math.log(p) / Math.log(2);
    }
    public static double W(int n) {
        return n * log2(n);
    }
    public static double S(int n) {
        return log2(n) * log2(n);
    }
    public static double T(int n, int p) {
        return W(n) / (W(n) / p + S(n));
    }
    public static void main(String[] args) {
        int n = 100_000_000;
        for (int i = 1; i <= 11; i++) {
            System.out.println(1 / (T(n, 1) / T(n, i)));
        }
    }
}
