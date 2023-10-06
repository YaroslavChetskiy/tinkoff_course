package edu.hw1;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static edu.hw1.Task4.fixString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Task4Test {

    @ParameterizedTest
    @DisplayName("Исправление Non Null строки")
    @CsvSource(value = {
        "123456, 214365",
        "hTsii  s aimex dpus rtni.g, This is a mixed up string.",
        "badce, abcde",
    })
    void fixNonNullString(String incorrectString, String expectedResult) {
        String actualResult = fixString(incorrectString);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("Пустая строка при передаче пустой строки")
    void getEmptyStringIfInputStringIsEmpty() {
        String actualResult = fixString("");
        assertThat(actualResult).isEqualTo("");
    }

    @Test
    @DisplayName("Выброс исключения при null")
    void throwNullPointerExceptionIfStringIsNull() {
        assertThrows(NullPointerException.class, () -> fixString(null));
    }

}
