package edu.hw1;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static edu.hw1.Task3.isNestable;
import static org.junit.jupiter.api.Assertions.*;

class Task3Test {

    @ParameterizedTest
    @DisplayName("Проверка вложенности в Non Null массивах")
    @MethodSource("getArgumentsForCheckNestableTest")
    void checkNestableTest(int[] array1, int[] array2, boolean expectedResult) {
        boolean actualResult = isNestable(array1, array2);
        Assertions.assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForCheckNestableTest() {
        return Stream.of(
            Arguments.of(new int[] {1, 2, 3, 4}, new int[] {0, 6}, true),
            Arguments.of(new int[] {3, 1}, new int[] {4, 0}, true),
            Arguments.of(new int[] {9, 9, 8}, new int[] {8, 9}, false),
            Arguments.of(new int[] {1, 2, 3, 4}, new int[] {2, 3}, false)
        );
    }

    @Test
    @DisplayName("Выброс исключения при null")
    void throwNullPointerExceptionIfArrayIsNull() {
        assertAll(
            () -> assertThrows(NullPointerException.class, () -> isNestable(null, new int[] {1, 2, 3})),
            () -> assertThrows(NullPointerException.class, () -> isNestable(new int[] {1, 2, 3}, null))
        );
    }
}
