package edu.hw5.task8;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;
import static edu.hw5.task8.Task8.hasAtLeastTwoZerosAndNoMoreThanOneOnes;
import static edu.hw5.task8.Task8.hasNoOnesSequence;
import static edu.hw5.task8.Task8.hasOddLength;
import static edu.hw5.task8.Task8.hasOddLengthAndFirstZeroOrHasEvenLengthAndFirstOne;
import static edu.hw5.task8.Task8.isNo11Or111String;
import static edu.hw5.task8.Task8.isZeroCountIsMultipleOfThree;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Task8Test {

    @ParameterizedTest
    @DisplayName("Имеет нечётную длину")
    @MethodSource("getArgumentsForHasOddLengthTest")
    void checkHasOddLength(String string, boolean expectedResult) {
        var actualResult = hasOddLength(string);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForHasOddLengthTest() {
        return Stream.of(
            Arguments.of("1", true),
            Arguments.of("000", true),
            Arguments.of("11011", true),
            Arguments.of("10", false),
            Arguments.of("1000", false),
            Arguments.of("12021", false),
            Arguments.of("101010", false),
            Arguments.of("", false),
            Arguments.of(null, false)
        );
    }

    @ParameterizedTest
    @DisplayName("Начинается на 0 и нечётная длина или начинается на 1 и чётная длина")
    @MethodSource("getArgumentsForHasOddLengthAndFirstZeroOrHasEvenLengthAndFirstOneTest")
    void checkHasOddLengthAndFirstZeroOrHasEvenLengthAndFirstOne(String string, boolean expectedResult) {
        var actualResult = hasOddLengthAndFirstZeroOrHasEvenLengthAndFirstOne(string);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForHasOddLengthAndFirstZeroOrHasEvenLengthAndFirstOneTest() {
        return Stream.of(
            Arguments.of("01111", true),
            Arguments.of("000", true),
            Arguments.of("10", true),
            Arguments.of("0", true),
            Arguments.of("1001", true),
            Arguments.of("1222", false),
            Arguments.of("0101", false),
            Arguments.of("", false),
            Arguments.of(null, false)
        );
    }

    @ParameterizedTest
    @DisplayName("Количество нулей кратно 3")
    @MethodSource("getArgumentsForIsZeroCountIsMultipleOfThreeTest")
    void checkIsZeroCountIsMultipleOfThree(String string, boolean expectedResult) {
        var actualResult = isZeroCountIsMultipleOfThree(string);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForIsZeroCountIsMultipleOfThreeTest() {
        return Stream.of(
            Arguments.of("1000", true),
            Arguments.of("000", true),
            Arguments.of("1111", true),
            Arguments.of("1000100011000", true),
            Arguments.of("1", true),
            Arguments.of("10", false),
            Arguments.of("2222", false),
            Arguments.of("101", false),
            Arguments.of("", false),
            Arguments.of(null, false)
        );
    }

    @ParameterizedTest
    @DisplayName("Любая строка, кроме 11 и 111")
    @MethodSource("getArgumentsForIsNo11Or111StringTest")
    void checkIsNo11Or111String(String string, boolean expectedResult) {
        var actualResult = isNo11Or111String(string);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForIsNo11Or111StringTest() {
        return Stream.of(
            Arguments.of("100", true),
            Arguments.of("000", true),
            Arguments.of("11111", true),
            Arguments.of("11", false),
            Arguments.of("111", false),
            Arguments.of("12021", false),
            Arguments.of("222", false),
            Arguments.of("", false),
            Arguments.of(null, false)
        );
    }

    @ParameterizedTest
    @DisplayName("Имеет минимум 2 нуля и не больше 1 единицы")
    @MethodSource("getArgumentsForHasAtLeastTwoZerosAndNoMoreThanOneOnesTest")
    void checkHasAtLeastTwoZerosAndNoMoreThanOneOnes(String string, boolean expectedResult) {
        var actualResult = hasAtLeastTwoZerosAndNoMoreThanOneOnes(string);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForHasAtLeastTwoZerosAndNoMoreThanOneOnesTest() {
        return Stream.of(
            Arguments.of("100", true),
            Arguments.of("000", true),
            Arguments.of("00100", true),
            Arguments.of("000001", true),
            Arguments.of("101", false),
            Arguments.of("12021", false),
            Arguments.of("111", false),
            Arguments.of("100100", false),
            Arguments.of("00010001", false),
            Arguments.of("", false),
            Arguments.of(null, false)
        );
    }

    @ParameterizedTest
    @DisplayName("Не имеет последовательных единиц")
    @MethodSource("getArgumentsForHasNoOnesSequenceTest")
    void checkHasNoOnesSequence(String string, boolean expectedResult) {
        var actualResult = hasNoOnesSequence(string);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForHasNoOnesSequenceTest() {
        return Stream.of(
            Arguments.of("1000", true),
            Arguments.of("10101", true),
            Arguments.of("01000", true),
            Arguments.of("000", true),
            Arguments.of("1", true),
            Arguments.of("11", false),
            Arguments.of("0011100", false),
            Arguments.of("001111", false),
            Arguments.of("12021", false),
            Arguments.of("", false),
            Arguments.of(null, false)
        );
    }
}
