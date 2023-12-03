package edu.hw9.task3.solver;

import edu.project2.maze.Coordinate;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static edu.project2.TestMazeUtil.TEST_MAZE;
import static org.assertj.core.api.Assertions.assertThat;

class MultiThreadDfsSolverTest {

    private static final int NUMBER_OF_THREADS = 5;

    @ParameterizedTest
    @DisplayName("Получение ожидаемого пути")
    @MethodSource("getArgumentsForGetCorrectPathTest")
    void getCorrectPath(Coordinate start, Coordinate end, List<Coordinate> expectedResult) {
        var mazeSolver = new MultiThreadDfsSolver(NUMBER_OF_THREADS);
        var actualResult = mazeSolver.solve(TEST_MAZE, start, end);
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
