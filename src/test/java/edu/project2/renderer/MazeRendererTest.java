package edu.project2.renderer;

import edu.project2.maze.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;
import static edu.project2.TestMazeUtil.MAZE_PATTERN;
import static edu.project2.TestMazeUtil.TEST_MAZE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MazeRendererTest {

    public static final MazeRenderer MAZE_RENDERER = MazeRenderer.getInstance();

    @Test
    @DisplayName("Выброс исключения, если путь - null")
    void throwIllegalArgumentExceptionIfPathIsNull() {
        var exception =
            assertThrows(IllegalArgumentException.class, () -> MAZE_RENDERER.render(TEST_MAZE, null));
        assertThat(exception.getMessage()).isEqualTo("Invalid path");
    }

    @Test
    @DisplayName("Получение нужной отрисовки лабиринта без пути")
    void getCorrectMazeRepresentation() {
        var actualResult = MAZE_RENDERER.render(TEST_MAZE);
        assertThat(actualResult).isEqualTo(MAZE_PATTERN);
    }

    @ParameterizedTest
    @DisplayName("Получение нужной отрисовки лабиринта с путём")
    @MethodSource("getArgumentsForMazeRepresentationWithPathTest")
    void getCorrectMazeRepresentationWithPath(List<Coordinate> path, String expectedResult) {
        var actualResult = MAZE_RENDERER.render(TEST_MAZE, path);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForMazeRepresentationWithPathTest() {
        return Stream.of(
            Arguments.of(
                List.of(
                    new Coordinate(1, 1),
                    new Coordinate(2, 1),
                    new Coordinate(3, 1),
                    new Coordinate(3, 2),
                    new Coordinate(3, 3)
                ),
                """
                    ███████
                    █·█   █
                    █·█ █ █
                    █···█ █
                    █████ █
                    █     █
                    ███████
                    """.replaceAll("\n", System.lineSeparator())
            ),
            Arguments.of(
                List.of(
                    new Coordinate(3, 3),
                    new Coordinate(2, 3),
                    new Coordinate(1, 3),
                    new Coordinate(1, 4),
                    new Coordinate(1, 5),
                    new Coordinate(2, 5),
                    new Coordinate(3, 5),
                    new Coordinate(4, 5),
                    new Coordinate(5, 5),
                    new Coordinate(5, 4),
                    new Coordinate(5, 3),
                    new Coordinate(5, 2),
                    new Coordinate(5, 1)
                ),
                """
                    ███████
                    █ █···█
                    █ █·█·█
                    █  ·█·█
                    █████·█
                    █·····█
                    ███████
                    """.replaceAll("\n", System.lineSeparator())
            )
        );
    }
}
