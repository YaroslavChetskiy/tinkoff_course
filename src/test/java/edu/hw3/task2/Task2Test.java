package edu.hw3.task2;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;
import static edu.hw3.task2.Task2.clusterBrackets;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Task2Test {

    @ParameterizedTest
    @DisplayName("Кластеризация скобок")
    @MethodSource("getArgumentsForBracketsClusteringTest")
    void checkBracketsClustering(String inputString, List<String> expectedResult) {
        var actualResult = clusterBrackets(inputString);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForBracketsClusteringTest() {
        return Stream.of(
            Arguments.of("()()()", List.of("()", "()", "()")),
            Arguments.of("((()))", List.of("((()))")),
            Arguments.of(
                "((()))(())()(()())",
                List.of("((()))", "(())", "()", "(()())")
            ),
            Arguments.of(
                "((())())(()(()()))",
                List.of("((())())", "(()(()()))")
            ),
            Arguments.of(")))((()))", List.of("((()))")),
            Arguments.of("))((", List.of()),
            Arguments.of(null, List.of()),
            Arguments.of("", List.of())
        );
    }

}
