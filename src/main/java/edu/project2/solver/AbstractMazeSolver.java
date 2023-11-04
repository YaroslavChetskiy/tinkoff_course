package edu.project2.solver;

import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import static edu.project2.maze.Type.PASSAGE;

public abstract class AbstractMazeSolver implements MazeSolver {

    protected static final int[] DX = {1, -1, 0, 0};
    protected static final int[] DY = {0, 0, 1, -1};

    private boolean isInvalidCellCoordinate(Coordinate coordinate, Maze maze) {
        return coordinate.column() < 0 || coordinate.row() < 0
            || coordinate.column() >= maze.getWidth() || coordinate.row() >= maze.getHeight()
            || maze.getGrid()[coordinate.row()][coordinate.row()].type() != PASSAGE;
    }

    protected void validateStartAndEndCells(Coordinate start, Coordinate end, Maze maze) {
        if (isInvalidCellCoordinate(start, maze) || isInvalidCellCoordinate(end, maze)) {
            throw new IllegalArgumentException("Invalid coordinates");
        }
    }
}
