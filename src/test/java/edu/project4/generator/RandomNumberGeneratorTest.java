package edu.project4.generator;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

class RandomNumberGeneratorTest {

    @ParameterizedTest
    @DisplayName("Получение случайного вещественного числа в заданном диапазоне")
    @MethodSource("getArgumentsForGetRandomDoubleTest")
    void getRandomDouble(double left_bound, double right_bound) {
        var generator = new RandomNumberGenerator(ThreadLocalRandom.current());
        var actualResult = generator.getRandomDouble(left_bound, right_bound);
        assertThat(actualResult).isBetween(left_bound, right_bound);
    }

    static Stream<Arguments> getArgumentsForGetRandomDoubleTest() {
        return Stream.of(
            Arguments.of(0, 1),
            Arguments.of(-1, 1),
            Arguments.of(-1.5, 1.5),
            Arguments.of(-3, 0),
            Arguments.of(-3, -2)
        );
    }

    @ParameterizedTest
    @DisplayName("Получение случайного целого числа в заданном диапазоне")
    @MethodSource("getArgumentsForGetRandomIntTest")
    void getRandomInt(int left_bound, int right_bound) {
        var generator = new RandomNumberGenerator(ThreadLocalRandom.current());
        var actualResult = generator.getRandomInt(left_bound, right_bound);
        assertThat(actualResult).isBetween(left_bound, right_bound);
    }

    static Stream<Arguments> getArgumentsForGetRandomIntTest() {
        return Stream.of(
            Arguments.of(0, 1),
            Arguments.of(-1, 1),
            Arguments.of(-15, 15),
            Arguments.of(-3, 0),
            Arguments.of(-3, -2)
        );
    }
}
