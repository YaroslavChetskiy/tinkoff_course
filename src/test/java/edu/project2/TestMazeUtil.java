package edu.project2;

import edu.project2.maze.Cell;
import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import edu.project2.maze.Type;

public class TestMazeUtil {
    public static final int MAZE_SIDE_SIZE = 7;

    public static final String MAZE_PATTERN = """
        ███████
        █ █   █
        █ █ █ █
        █   █ █
        █████ █
        █     █
        ███████
        """.replace("\n", System.lineSeparator());

    public static Maze TEST_MAZE;

    static {
        prepareTestMaze();
    }

    public static void prepareTestMaze() {
        Cell[][] grid = new Cell[MAZE_SIDE_SIZE][MAZE_SIDE_SIZE];
        int y = 0;
        for (int x = 0; x < MAZE_SIDE_SIZE; x++) {
            switch (MAZE_PATTERN.charAt(y * (MAZE_SIDE_SIZE + System.lineSeparator().length()) + x)) {
                case '█' -> grid[y][x] = new Cell(new Coordinate(y, x), Type.WALL);
                case ' ' -> grid[y][x] = new Cell(new Coordinate(y, x), Type.PASSAGE);
            }
            if (x == MAZE_SIDE_SIZE - 1) {
                if (y == MAZE_SIDE_SIZE - 1) {
                    break;
                } else {
                    x = -1;
                    y++;
                }
            }
        }
        TEST_MAZE = new Maze(MAZE_SIDE_SIZE, MAZE_SIDE_SIZE, grid);
    }
}
