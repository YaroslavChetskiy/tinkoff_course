package edu.hw1;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static edu.hw1.Task1.minutesToSeconds;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Task1Test {

    // Под правильным форматом имеется mm:ss
    @ParameterizedTest
    @DisplayName("Возвращение полной длины видео в корректной по формату строке")
    @CsvSource(value = {
        "01:00, 60",
        "13:56, 836",
        "10:60, -1",
        "12:44, 764",
        "20:99, -1"
    })
    void getCorrectTimeIfStringHasValidFormat(String videoLength, int expectedResult) {
        int actualResult = minutesToSeconds(videoLength);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("Выброс исключения при передача null в функцию")
    void throwNullPointerExceptionIfStringIsNull() {
        assertThrows(NullPointerException.class, () -> minutesToSeconds(null));
    }

    @ParameterizedTest
    @DisplayName("Выброс исключения при неверном формате строки")
    @CsvSource(value = {
        "-1:-2",
        "hello:world",
        "10:10:10",
        "01:9999"
    })
    void throwIllegalArgumentExceptionIfStringHasInvalidFormat(String videoLength) {
        var exception = assertThrows(IllegalArgumentException.class, () -> minutesToSeconds(videoLength));
        assertThat(exception.getMessage()).isEqualTo("Invalid format");
    }
}
