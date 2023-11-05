package edu.project2.generator;

import edu.project2.maze.Cell;
import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import edu.project2.maze.Type;
import java.util.ArrayList;
import java.util.List;
import static edu.project2.maze.Type.WALL;

public abstract class AbstractMazeGenerator implements MazeGenerator {

    private static final int MIN_SIDE_SIZE = 5;
    // при большем 147 у меня уже в консоль не помещается,
    // поэтому решил хоть как-то ограничить сверху, но не вплотную к 147
    private static final int MAX_SIDE_SIZE = 121;

    protected static final Coordinate START_CELL_COORDINATE = new Coordinate(1, 1);
    protected static final IntegerGenerator DEFAULT_INTEGER_GENERATOR =
        new IntegerGenerator(System.currentTimeMillis());

    protected final IntegerGenerator integerGenerator;
    protected Maze generatedMaze;

    protected AbstractMazeGenerator(int height, int width) {
        this(height, width, DEFAULT_INTEGER_GENERATOR);
    }

    protected AbstractMazeGenerator(int height, int width, IntegerGenerator integerGenerator) {
        this.integerGenerator = integerGenerator;
        Cell[][] grid = getEmptyGrid(height, width);
        this.generatedMaze = new Maze(height, width, grid);
    }

    protected boolean isInsideCell(int x, int y, int height, int width) {
        return x >= START_CELL_COORDINATE.column() && y >= START_CELL_COORDINATE.row()
            && x < width - 1 && y < height - 1;
    }

    protected Cell[][] getEmptyGrid(int height, int width) {
        validateSize(height, width);
        Cell[][] grid = new Cell[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = new Cell(new Coordinate(y, x), Type.WALL);
            }
        }
        return grid;
    }

    protected List<Coordinate> getNeighbours(Coordinate cellCoordinates, Maze maze) {
        List<Coordinate> neighbours = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            int newX = cellCoordinates.column() + direction.getDx();
            int newY = cellCoordinates.row() + direction.getDy();
            Coordinate neighbourCoords = new Coordinate(newY, newX);
            if (isInsideCell(newX, newY, maze.getHeight(), maze.getWidth())
                && maze.getGrid()[newY][newX].type() == WALL) {
                neighbours.add(neighbourCoords);
            }
        }
        return neighbours;
    }

    private void validateSize(int height, int width) {
        if (height < MIN_SIDE_SIZE || width < MIN_SIDE_SIZE
            || height > MAX_SIDE_SIZE || width > MAX_SIDE_SIZE) {
            throw new IllegalArgumentException("Invalid maze dimensions");
        }
    }
}
