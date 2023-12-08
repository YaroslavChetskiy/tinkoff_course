package edu.project4.model;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

class RectangleTest {

    private static final Rectangle TEST_RECTANGLE = new Rectangle(0, 0, 1920, 1080);

    @ParameterizedTest
    @DisplayName("Проверка вхождения точки в прямоугольник")
    @MethodSource("getArgumentsForContainsTest")
    void contains(Point point, boolean expectedResult) {
        var actualResult = TEST_RECTANGLE.contains(point);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForContainsTest() {
        return Stream.of(
            Arguments.of(new Point(0, 0), true),
            Arguments.of(new Point(500, 1000), true),
            Arguments.of(new Point(1919, 1079), true),
            Arguments.of(new Point(1920, 1080), false),
            Arguments.of(new Point(-100, 100), false),
            Arguments.of(new Point(100, -100), false),
            Arguments.of(new Point(2000, 100), false),
            Arguments.of(new Point(100, 2000), false)
        );
    }
}
