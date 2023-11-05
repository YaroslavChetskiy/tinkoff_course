package edu.project2.generator;

import edu.project2.maze.Cell;
import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import java.util.ArrayList;
import java.util.List;
import static edu.project2.maze.Type.PASSAGE;

public class RecursiveBacktrackerGenerator extends AbstractMazeGenerator {

    private final List<Coordinate> visitedCells;

    public RecursiveBacktrackerGenerator(int height, int width) {
        this(height, width, DEFAULT_INTEGER_GENERATOR);
    }

    public RecursiveBacktrackerGenerator(int height, int width, IntegerGenerator integerGenerator) {
        super(height, width, integerGenerator);
        visitedCells = new ArrayList<>();
    }

    @Override
    public Maze generate() {
        generatedMaze = new Maze(
            generatedMaze.getHeight(),
            generatedMaze.getWidth(),
            getEmptyGrid(generatedMaze.getHeight(), generatedMaze.getWidth())
        );
        visitedCells.clear();
        carvePassages(START_CELL_COORDINATE);
        return generatedMaze;
    }

    private void carvePassages(Coordinate currentCoords) {
        visitedCells.add(currentCoords);
        generatedMaze.getGrid()[currentCoords.row()][currentCoords.column()] = new Cell(currentCoords, PASSAGE);
        List<Coordinate> unvisitedNeighbours = getNeighbours(currentCoords, generatedMaze);
        while (!unvisitedNeighbours.isEmpty()) {
            int randomIndex = integerGenerator.getRandomValue(0, unvisitedNeighbours.size());
            Coordinate randomNeighbour = unvisitedNeighbours.remove(randomIndex);
            if (!visitedCells.contains(randomNeighbour)) {
                int wallRow = (currentCoords.row() + randomNeighbour.row()) / 2;
                int wallColumn = (currentCoords.column() + randomNeighbour.column()) / 2;

                generatedMaze.getGrid()[wallRow][wallColumn] = new Cell(new Coordinate(wallRow, wallColumn), PASSAGE);
                carvePassages(randomNeighbour);
            }
        }
    }
}
