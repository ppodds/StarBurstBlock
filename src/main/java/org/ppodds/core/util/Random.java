package org.ppodds.core.util;

public class Random {
    private static java.util.Random random = new java.util.Random();
    public static <T>T choose(T[] array) {
        return array[random.nextInt(array.length)];
    }
    public static char choose(char[] array) {
        return array[random.nextInt(array.length)];
    }
}
