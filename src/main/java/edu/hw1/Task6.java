package edu.hw1;

import java.util.Arrays;

public final class Task6 {

    private static final int MIN_NUMBER = 1000;
    private static final int MAX_NUMBER = 9998; // не 9999, т.к у 9999 все цифры одинаковые

    private static final int KAPREKAR_NUMBER = 6174;

    private Task6() {
    }

    public static int countK(int number) {
        var digits = Integer.toString(number).toCharArray();
        Arrays.sort(digits);
        // проверка на то, что все цифры одинаковые
        if (number < MIN_NUMBER || number > MAX_NUMBER || digits[0] == digits[digits.length - 1]) {
            throw new IllegalArgumentException("Invalid number");
        }
        int ascendant = Integer.parseInt(String.valueOf(digits));
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(digits));
        int descendant = Integer.parseInt(stringBuilder.reverse().toString());
        int difference = Math.max(ascendant, descendant) - Math.min(ascendant, descendant);
        if (difference == KAPREKAR_NUMBER) {
            return 1;
        }
        return 1 + countK(difference);
    }
}
