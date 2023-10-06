package edu.hw1;

public final class Task3 {

    private Task3() {
    }

    public static boolean isNestable(int[] array1, int[] array2) {
        if (array1 == null || array2 == null) {
            throw new NullPointerException();
        }
        // Не прошли stream'ы, поэтому найдём через циклы
        int array1Min = Integer.MAX_VALUE;
        int array2Min = Integer.MAX_VALUE;
        int array1Max = Integer.MIN_VALUE;
        int array2Max = Integer.MIN_VALUE;
        for (int element : array1) {
            array1Min = Math.min(array1Min, element);
            array1Max = Math.max(array1Max, element);
        }

        for (int element : array2) {
            array2Min = Math.min(array2Min, element);
            array2Max = Math.max(array2Max, element);
        }
        return array1Min > array2Min && array1Max < array2Max;
    }
}
