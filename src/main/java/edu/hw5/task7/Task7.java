package edu.hw5.task7;

public final class Task7 {

    private static final String FIRST_REGEX = "^[01]{2}0[01]*$";

    private static final String SECOND_REGEX = "(^[01]$)|^([01])[01]*\\2$";
    private static final String THIRD_REGEX = "^[01]{1,3}$";

    private Task7() {
    }

    public static boolean containsAtLeastThreeCharactersWithThirdZero(String string) {
        return string != null && string.matches(FIRST_REGEX);
    }

    public static boolean startsWithEndCharacter(String string) {
        return string != null && string.matches(SECOND_REGEX);
    }

    public static boolean lengthNoLessThanOneAndNoMoreThanThree(String string) {
        return string != null && string.matches(THIRD_REGEX);
    }
}
