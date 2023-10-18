package edu.hw2.task3.generator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class IntegerGeneratorTest {

    @ParameterizedTest
    @DisplayName("Генерация случайного целого числа в заданных пределах")
    @CsvSource(value = {
        "0, 100",
        "-100, 0",
        "-321, -123",
        "128, 256",
        "-8, 16",
    })
    void getRandomValue(int leftBound, int rightBound) {
        var integerGenerator = new IntegerGenerator();
        int actualResult = integerGenerator.getRandomValue(leftBound, rightBound);
        assertThat(actualResult).isBetween(leftBound, rightBound - 1);
    }
}
