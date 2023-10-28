package edu.hw3.task4;

import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("MagicNumber")
public final class Task4 {

    private static final int MAX_ARABIC_NUMBER = 3999;

    private static final Map<Integer, String> ARABIC_TO_ROMAN_MAP = new TreeMap<>(Map.ofEntries(
        Map.entry(1, "I"),
        Map.entry(4, "IV"),
        Map.entry(5, "V"),
        Map.entry(9, "IX"),
        Map.entry(10, "X"),
        Map.entry(40, "XL"),
        Map.entry(50, "L"),
        Map.entry(90, "XC"),
        Map.entry(100, "C"),
        Map.entry(400, "CD"),
        Map.entry(500, "D"),
        Map.entry(900, "CM"),
        Map.entry(1000, "M")
    )
    ).descendingMap();

    private Task4() {
    }

    public static String convertToRoman(int arabicNumber) {
        if (arabicNumber <= 0 || arabicNumber > MAX_ARABIC_NUMBER) {
            throw new IllegalArgumentException("Invalid input number");
        }
        var stringBuilder = new StringBuilder();
        int temp = arabicNumber;
        for (var entry : ARABIC_TO_ROMAN_MAP.entrySet()) {
            if (temp == 0) {
                break;
            }
            while (entry.getKey() <= temp) {
                temp -= entry.getKey();
                stringBuilder.append(entry.getValue());
            }
        }
        return stringBuilder.toString();
    }
}
