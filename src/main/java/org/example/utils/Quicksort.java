package org.example.utils;

public class Quicksort {
    public static <T extends Comparable<T>> void quickSort(T[] array, int l, int h) {
        if (l < h) {
            int pivot = partition(array, l, h);
            quickSort(array, l, pivot - 1);
            quickSort(array, pivot + 1, h);
        }
    }

    public static <T extends Comparable<T>> int partition(T[] array, int l, int h) {
        T pivot = array[h];
        int i = l - 1;
        for (int j = l; j < h; j++) {
            if (array[j].compareTo(pivot) <= 0) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, h);
        return i + 1;
    }

    private static <T extends Comparable<T>> void swap(T[] array, int i, int j) {
        T t = array[i];
        array[i] = array[j];
        array[j] = t;
    }
}
