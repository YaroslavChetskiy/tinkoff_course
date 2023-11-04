package edu.project2.solver;

import edu.project2.maze.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;
import static edu.project2.TestMazeUtil.TEST_MAZE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MazeSolverTest {

    private static final MazeSolver MAZE_SOLVER = DfsSolver.getInstance();

    @ParameterizedTest
    @DisplayName("Выброс исключения при невалидных точках начала и конца")
    @MethodSource("getArgumentsForInvalidCoordinatesTest")
    void throwIllegalArgumentExceptionIfCoordinatesIsInvalid(Coordinate start, Coordinate end) {
        var exception =
            assertThrows(IllegalArgumentException.class, () -> MAZE_SOLVER.solve(TEST_MAZE, start, end));
        assertThat(exception.getMessage()).isEqualTo("Invalid coordinates");
    }

    static Stream<Arguments> getArgumentsForInvalidCoordinatesTest() {
        return Stream.of(
            Arguments.of(
                new Coordinate(1, 1),
                new Coordinate(4, 1)
            ),
            Arguments.of(
                new Coordinate(99, 99),
                new Coordinate(1, 1)
            ),
            Arguments.of(
                new Coordinate(0, 0),
                new Coordinate(7, 7)
            ),
            Arguments.of(
                new Coordinate(-100, 0),
                new Coordinate(0, -100)
            )
        );
    }

    @ParameterizedTest
    @DisplayName("Получение ожидаемого пути")
    @MethodSource("getArgumentsForGetCorrectPathTest")
    void getCorrectPath(Coordinate start, Coordinate end, List<Coordinate> expectedResult) {
        var actualResult = MAZE_SOLVER.solve(TEST_MAZE, start, end);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForGetCorrectPathTest() {
        return Stream.of(
            Arguments.of(
                new Coordinate(1, 1),
                new Coordinate(3, 3),
                List.of(
                    new Coordinate(1, 1),
                    new Coordinate(2, 1),
                    new Coordinate(3, 1),
                    new Coordinate(3, 2),
                    new Coordinate(3, 3)
                )
            ),
            Arguments.of(
                new Coordinate(3, 3),
                new Coordinate(5, 1),
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
                )
            )
        );
    }
}
