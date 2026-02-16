package org.example.utils;

import java.util.Random;

public class Quicksort {
    public static <T extends Comparable<T>> void quickSort(T[] array, int l, int h) {
        if (l < h) {
            int[] pivots = partition(array, l, h);
            quickSort(array, l, pivots[0] - 1);
            quickSort(array, pivots[1] + 1, h);
        }
    }

    public static <T extends Comparable<T>> int[] partition(T[] array, int l, int h) {
        int pivotIndex = l + new Random().nextInt(h - l + 1);

        swap(array, pivotIndex, h);
        T pivot = array[h];
        int L = l;
        int i = l;
        int R = h;

        while (i <= R) {
            int cmp = array[i].compareTo(pivot);
            if (cmp < 0) {
                swap(array, L, i);
                L++;
                i++;
            } else if (cmp > 0) {
                swap(array, i, R);
                R--;
            } else {
                i++;
            }
        }
        return new int[]{L, R};
    }

    private static <T extends Comparable<T>> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
