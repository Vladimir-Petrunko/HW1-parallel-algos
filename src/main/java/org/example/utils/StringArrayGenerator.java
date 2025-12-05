package org.example.utils;

public class IntegerArrayGenerator {
    public static Integer[] generateArray(int size) {
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int)(Math.random() * 123456789);
        }
        return array;
    }
}
