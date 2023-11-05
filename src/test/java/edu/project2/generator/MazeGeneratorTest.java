package edu.project2.generator;

import edu.project2.maze.Maze;
import edu.project2.maze.Type;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MazeGeneratorTest {

    public static final String INVALID_MAZE_DIMENSIONS = "Invalid maze dimensions";

    @ParameterizedTest
    @DisplayName("Выброс исключения при неправильных размерах")
    @CsvSource(value = {
        "-100, 100",
        "100, -100",
        "999, -100",
        "100, 999",
        "1, 2",
        "0, 0",
    })
    void throwIllegalArgumentExceptionIfDimensionsIsInvalid(int height, int width) {
        var primsException = assertThrows(
            IllegalArgumentException.class,
            () -> new PrimsGenerator(height, width)
        );
        var backtrackerException = assertThrows(
            IllegalArgumentException.class,
            () -> new RecursiveBacktrackerGenerator(height, width)
        );
        assertThat(primsException.getMessage()).isEqualTo(INVALID_MAZE_DIMENSIONS);
        assertThat(backtrackerException.getMessage()).isEqualTo(INVALID_MAZE_DIMENSIONS);
    }

    @ParameterizedTest
    @DisplayName("Получение целостного лабиринта (без проходов на границах)")
    @MethodSource("getArgumentsForCompleteMazeTest")
    void getCompleteMaze(int height, int width) {
        for (MazeGenerator generator : List.of(
            new PrimsGenerator(height, width, new IntegerGenerator()),
            new PrimsGenerator(height, width),
            new RecursiveBacktrackerGenerator(height, width, new IntegerGenerator(1L)),
            new RecursiveBacktrackerGenerator(height, width)
        )) {
            Maze maze = generator.generate();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
                        assertThat(maze.getGrid()[y][x].type()).isEqualTo(Type.WALL);
                    }
                }
            }
        }
    }

    static Stream<Arguments> getArgumentsForCompleteMazeTest() {
        return Stream.of(
            Arguments.of(9, 9),
            Arguments.of(50, 50),
            Arguments.of(51, 31),
            Arguments.of(21, 41),
            Arguments.of(30, 30),
            Arguments.of(121, 101)
        );
    }
}
