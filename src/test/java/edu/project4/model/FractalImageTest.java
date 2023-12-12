package edu.project4.model;

import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

class FractalImageTest {

    private static final FractalImage TEST_IMAGE = FractalImage.create(1920, 1080);

    @Test
    @DisplayName("Создание стартового изображения")
    void create() {
        Pixel[] data = new Pixel[TEST_IMAGE.width() * TEST_IMAGE.height()];
        Arrays.fill(data, new Pixel(0, 0, 0, 0));
        var actualResult = FractalImage.create(TEST_IMAGE.width(), TEST_IMAGE.height());
        assertThat(actualResult.data()).isEqualTo(data);
    }

    @ParameterizedTest
    @DisplayName("Проверка вхождения пикселя в изображение")
    @MethodSource("getArgumentsForContainsTest")
    void contains(int x, int y, boolean expectedResult) {
        var actualResult = TEST_IMAGE.contains(x, y);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("Взятие пикселя по координатам")
    void getPixel() {
        var firstX = 100;
        var firstY = 100;
        var actualResult = TEST_IMAGE.getPixel(firstX, firstY);
        var expectedResult = new Pixel(0, 0, 0, 0);
        assertThat(actualResult).isEqualTo(expectedResult);

        var notInsideX = -100;
        var notInsideY = -100;
        actualResult = TEST_IMAGE.getPixel(notInsideX, notInsideY);
        assertThat(actualResult).isNull();
    }

    static Stream<Arguments> getArgumentsForContainsTest() {
        return Stream.of(
            Arguments.of(0, 0, true),
            Arguments.of(500, 1000, true),
            Arguments.of(1919, 1079, true),
            Arguments.of(1920, 1080, false),
            Arguments.of(-100, 100, false),
            Arguments.of(100, -100, false),
            Arguments.of(2000, 100, false),
            Arguments.of(100, 2000, false)
        );
    }
}
