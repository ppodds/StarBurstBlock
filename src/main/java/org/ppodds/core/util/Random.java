package org.ppodds.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Random {
    private static java.util.Random random = new java.util.Random();
    public static <T>T choose(T[] array) {
        return array[random.nextInt(array.length)];
    }
    public static char choose(char[] array) {
        return array[random.nextInt(array.length)];
    }
    public static <T>ArrayList<T> shuffle(ArrayList<T> array) {
        ArrayList<T> list = new ArrayList(array);
        ArrayList<T> shuffled = new ArrayList<T>();
        for (int i = array.size(); i>0;i--) {
            int picked = random.nextInt(i);
            shuffled.add(list.get(picked));
            list.remove(picked);
        }
        return shuffled;
    }
}
