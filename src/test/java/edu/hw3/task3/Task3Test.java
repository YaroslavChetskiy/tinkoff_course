package edu.hw3.task3;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import static edu.hw3.task3.Task3.getFrequencyDictionary;
import static org.assertj.core.api.Assertions.assertThat;

class Task3Test {

    @ParameterizedTest
    @DisplayName("Получение частотного словаря")
    @MethodSource("getArgumentsForGetFrequencyDictionaryTest") <T> void testGetFrequencyDictionary(
        List<T> inputList,
        Map<T, Integer> expectedResult
    ) {
        var actualResult = getFrequencyDictionary(inputList);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForGetFrequencyDictionaryTest() {
        return Stream.of(
            Arguments.of(
                List.of("a", "bb", "a", "bb"),
                Map.of("bb", 2, "a", 2)
            ),
            Arguments.of(
                List.of(),
                Map.of()
            ),
            Arguments.of(
                List.of(1, 1, 2, 2),
                Map.of(1, 2, 2, 2)
            ),
            Arguments.of(
                List.of("this", "and", "that", "and"),
                Map.of("this", 1, "and", 2, "that", 1)
            ),
            Arguments.of(
                List.of("one", "one", "one", "one"),
                Map.of("one", 4)
            ),
            Arguments.of(
                null,
                Map.of()
            )
        );
    }

}
