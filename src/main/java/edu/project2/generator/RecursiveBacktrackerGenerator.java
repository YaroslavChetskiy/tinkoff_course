package edu.project2.generator;

import edu.project2.maze.Cell;
import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import java.util.ArrayList;
import java.util.List;
import static edu.project2.maze.Type.PASSAGE;

public class RecursiveBacktrackerGenerator extends AbstractMazeGenerator {

    private static final RecursiveBacktrackerGenerator INSTANCE = new RecursiveBacktrackerGenerator();

    // чтобы не передавать их постоянно в функции
    private List<Coordinate> visitedCells;
    private Maze generatedMaze;

    private RecursiveBacktrackerGenerator() {
    }

    public static RecursiveBacktrackerGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public Maze generate(int height, int width) {
        Cell[][] grid = getEmptyGrid(height, width);
        visitedCells = new ArrayList<>();
        generatedMaze = new Maze(height, width, grid);
        carvePassages(START_CELL_COORDINATE);
        return generatedMaze;
    }

    private void carvePassages(Coordinate currentCoords) {
        visitedCells.add(currentCoords);
        generatedMaze.getGrid()[currentCoords.row()][currentCoords.column()] = new Cell(currentCoords, PASSAGE);
        List<Coordinate> unvisitedNeighbours = getNeighbours(currentCoords, generatedMaze);
        while (!unvisitedNeighbours.isEmpty()) {
            int randomIndex = RANDOM.nextInt(unvisitedNeighbours.size());
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
