package edu.hw5.task8;

public final class Task8 {

    private static final String FIRST_REGEX = "^[01]([01][01])*$";
    private static final String SECOND_REGEX = "^(0([01][01])*)$|^(1([01][01])*[01])$";
    private static final String THIRD_REGEX = "^1+$|^(1*01*01*01*)+$";
    private static final String FOURTH_REGEX = "^(?!^(11|111)$)([01]+)$";
    private static final String FIFTH_REGEX = "^0{2,}$|^0*(100|010|001)0*$";
    private static final String SIXTH_REGEX = "^0+$|^(0*(?!1{2,})10*)+$";

    private Task8() {
    }

    public static boolean hasOddLength(String string) {
        return string != null && string.matches(FIRST_REGEX);
    }

    public static boolean hasOddLengthAndFirstZeroOrHasEvenLengthAndFirstOne(String string) {
        return string != null && string.matches(SECOND_REGEX);
    }

    public static boolean isZeroCountIsMultipleOfThree(String string) {
        return string != null && string.matches(THIRD_REGEX);
    }

    public static boolean isNo11Or111String(String string) {
        return string != null && string.matches(FOURTH_REGEX);
    }

    public static boolean hasAtLeastTwoZerosAndNoMoreThanOneOnes(String string) {
        return string != null && string.matches(FIFTH_REGEX);
    }

    public static boolean hasNoOnesSequence(String string) {
        return string != null && string.matches(SIXTH_REGEX);
    }
}
