package edu.hw5.task5;

public final class Task5 {

    private static final String RUSSIAN_CAR_NUMBER_REGEX = "[АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2}\\d{2,3}";

    private Task5() {
    }

    public static boolean validateRussianCarNumber(String number) {
        return number != null && number.matches(RUSSIAN_CAR_NUMBER_REGEX);
    }
}
