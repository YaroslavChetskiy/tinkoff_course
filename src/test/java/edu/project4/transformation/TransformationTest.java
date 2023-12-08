package edu.project4.transformation;

import edu.project4.model.Point;
import java.util.stream.Stream;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

class TransformationTest {

    private static final Offset<Double> OFFSET = Offset.offset(0.0001);

    @ParameterizedTest
    @DisplayName("Получение преобразованной по формуле координаты")
    @MethodSource("getArgumentsForGetTransformedPointTest")
    void getTransformedPoint(Transformation transformation, Point point, Point expectedResult) {
        var actualResult = transformation.apply(point);
        assertThat(actualResult.x()).isEqualTo(expectedResult.x(), OFFSET);
        assertThat(actualResult.y()).isEqualTo(expectedResult.y(), OFFSET);
    }

    static Stream<Arguments> getArgumentsForGetTransformedPointTest() {
        return Stream.of(
            Arguments.of(
                new AffineTransformation(1, 2, 3, 4, 5, 6, 0, 0, 0),
                new Point(1, 1),
                new Point(6, 15)
            ),
            Arguments.of(
                new ExponentialTransformation(),
                new Point(1, 1),
                new Point(-1, 0)
            ),
            Arguments.of(
                new EyefishTransformation(),
                new Point(1, 1),
                new Point(0.8284, 0.8284)
            ),
            Arguments.of(
                new HeartTransformation(),
                new Point(1, 1),
                new Point(1.2671, -0.6279)
            ),
            Arguments.of(
                new SphericalTransformation(),
                new Point(1, 1),
                new Point(0.5, 0.5)
            ),
            Arguments.of(
                new SphericalTransformation(),
                new Point(0, 0),
                new Point(0, 0)
            ),
            Arguments.of(
                new SpiralTransformation(),
                new Point(1, 1),
                new Point(1.1984, 0.6102)
            ),
            Arguments.of(
                new SwirlTransformation(),
                new Point(1, 1),
                new Point(1.3254, 0.4931)
            ),
            Arguments.of(
                (Transformation) point -> point,
                new Point(1, 1),
                new Point(1, 1)
            )
        );
    }

}
