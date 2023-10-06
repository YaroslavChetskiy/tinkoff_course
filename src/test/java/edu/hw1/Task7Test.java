package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static edu.hw1.Task7.rotateLeft;
import static edu.hw1.Task7.rotateRight;
import static org.assertj.core.api.Assertions.assertThat;

class Task7Test {

    @ParameterizedTest
    @DisplayName("Циклический сдвиг вправо")
    @CsvSource(value = {
        "0, 3, 0",
        "8, 1, 4",
        "117, 3, 94",
        "6, 3, 6",
        "6, 4, 3",
    })
    void getCorrectRotatedRightNumber(int n, int shift, int expectedResult) {
        var actualResult = rotateRight(n, shift);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @DisplayName("Циклический сдвиг влево")
    @CsvSource(value = {
        "0, 3, 0",
        "16, 1, 1",
        "17, 2, 6",
        "6, 3, 6",
        "6, 4, 5",
    })
    void getCorrectRotatedLeftNumber(int n, int shift, int expectedResult) {
        var actualResult = rotateLeft(n, shift);
        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
