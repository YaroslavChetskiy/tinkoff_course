package edu.hw3.task4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static edu.hw3.task4.Task4.convertToRoman;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Task4Test {

    @ParameterizedTest
    @DisplayName("Преобразование арабского числа в римское")
    @CsvSource(value = {
        "2, II",
        "6, VI",
        "19, XIX",
        "321, CCCXXI",
        "901, CMI",
        "3525, MMMDXXV",
        "2494, MMCDXCIV",
        "3999, MMMCMXCIX"
    })
    void getCorrectRomanRepresentation(int arabicNumber, String expectedResult) {
        var actualResult = convertToRoman(arabicNumber);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @DisplayName("Выброс исключения при не подходящем числе")
    @ValueSource(ints = {-100, 0, 4000, 5454})
    void IllegalArgumentException(int arabicNumber) {
        assertThrows(IllegalArgumentException.class, () -> convertToRoman(arabicNumber));
    }
}
