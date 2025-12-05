package org.example.utils;

public class StringArrayGenerator {
    public static String[] generateArray(int size, int maxItemLength) {
        String[] array = new String[size];
        for (int i = 0; i < size; i++) {
            int length = (int)(Math.random() * (maxItemLength - 1)) + 1;
            StringBuilder builder = new StringBuilder(length);
            for (int j = 0; j < length; j++) {
                builder.append(randomChar());
            }
            array[i] = builder.toString();
        }
        return array;
    }

    private static char randomChar() {
        return (char)(Math.random() * 26 + 97);
    }
}
