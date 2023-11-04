package edu.project2.generator;

import edu.project2.maze.Cell;
import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static edu.project2.maze.Type.PASSAGE;

public class PrimsGenerator extends AbstractMazeGenerator {

    private static final PrimsGenerator INSTANCE = new PrimsGenerator();

    private PrimsGenerator() {
    }

    public static PrimsGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public Maze generate(int height, int width) {
        // Generation empty maze
        Cell[][] grid = getEmptyGrid(height, width);
        Maze generatedMaze = new Maze(height, width, grid);

        // start cell
        var row = RANDOM.nextInt(1, height - 1);
        var column = RANDOM.nextInt(1, width - 1);
        Coordinate randomStartCellCoords = new Coordinate(
            row + (height - row) % 2, // чтобы не было двойных стен на границах лабиринта
            column + (height - column) % 2 // хотя они в любом случае будут, если размеры лабиринта чётные
        );
        grid[randomStartCellCoords.row()][randomStartCellCoords.column()] = new Cell(randomStartCellCoords, PASSAGE);

        // frontier cells computing
        Set<Coordinate> frontierCells = new HashSet<>(getNeighbours(randomStartCellCoords, generatedMaze));
        while (!frontierCells.isEmpty()) {
            // get random neighbour
            int randomIndex = RANDOM.nextInt(frontierCells.size());
            Coordinate randomNeighbour = frontierCells.stream().toList().get(randomIndex);
            frontierCells.remove(randomNeighbour);
            grid[randomNeighbour.row()][randomNeighbour.column()] = new Cell(randomNeighbour, PASSAGE);

            // clear wall between frontier and passage cell
            List<Direction> directions = new ArrayList<>(List.of(Direction.values()));
            while (!directions.isEmpty()) {
                int randomDirectionIndex = RANDOM.nextInt(directions.size());
                Direction randomDirection = directions.get(randomDirectionIndex);
                int nx = randomNeighbour.column() + randomDirection.getDx();
                int ny = randomNeighbour.row() + randomDirection.getDy();

                if (isInsideCell(nx, ny, height, width) && grid[ny][nx].type() == PASSAGE) {
                    var yBetween = ny - randomDirection.getDy() / 2;
                    var xBetween = nx - randomDirection.getDx() / 2;
                    grid[yBetween][xBetween] = new Cell(new Coordinate(yBetween, xBetween), PASSAGE);
                    directions.clear();
                    break;
                }

                directions.remove(randomDirectionIndex);
            }

            frontierCells.addAll(getNeighbours(randomNeighbour, generatedMaze));
        }
        return generatedMaze;
    }
}
