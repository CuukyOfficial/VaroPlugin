package de.cuuky.varo.utils;

public class ArrayUtils {

    public static <T> T getNext(T search, T[] arr) {
        for (int i = 0; i < arr.length; i++)
            if (arr[i] == search)
                return arr[i == arr.length - 1 ? 0 : i];

        return null;
    }
}