package edu.hw2.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Task2Test {

    @ParameterizedTest
    @DisplayName("Тест из условия задания (на правильность реализации LSP)")
    @MethodSource("getArgumentsForRectangleAreaChecking")
    void checkRectangleArea(Rectangle rectangle) {
        rectangle = Rectangle.builder()
            .width(20)
            .height(10)
            .build();

        assertThat(rectangle.area()).isEqualTo(200);
    }

    static Arguments[] getArgumentsForRectangleAreaChecking() {
        return new Arguments[] {
            Arguments.of(new Rectangle()),
            Arguments.of(new Square())
        };
    }

    @Test
    @DisplayName("Выброс исключения при отрицательных размерах")
    void throwIllegalArgumentExceptionIfSideSizeLessThanZero() {
        var exception = assertThrows(IllegalArgumentException.class, () -> new Rectangle(-1, -1));
        assertThat(exception.getMessage()).isEqualTo("Invalid side sizes");
    }

    @ParameterizedTest
    @DisplayName("Проверка подсчёта площади квадрата")
    @CsvSource(value = {
        "1, 1",
        "10, 100",
        "2, 4"
    })
    void checkSquareArea(int side, int expectedResult) {
        var square = Square.builder()
            .side(side)
            .height(side) // не знаю насколько целесообразно тестировать их,
            .width(side) // разве что ради 100% покрытия тестов
            .build();
        int actualResult = square.area();
        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
