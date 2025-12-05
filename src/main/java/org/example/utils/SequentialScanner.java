package org.example.utils;

import java.util.function.BiFunction;

@SuppressWarnings("unchecked")
public class SequentialScanner<T> {
    public void scan(T[] array, BiFunction<T, T, T> f) {
        ScanResult.result[0] = null;
        for (int i = 1; i < array.length; i++) {
            ScanResult.result[i] = f.apply((T) ScanResult.result[i - 1], array[i - 1]);
        }
    }
}
