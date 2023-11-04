package edu.project2.solver;

import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import edu.project2.maze.Type;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DfsSolver extends AbstractMazeSolver {

    private static final int[] DX = {1, -1, 0, 0};
    private static final int[] DY = {0, 0, 1, -1};
    private static final DfsSolver INSTANCE = new DfsSolver();

    private DfsSolver() {
    }

    public static DfsSolver getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        validateStartAndEndCells(start, end, maze);

        Set<Coordinate> visitedCells = new HashSet<>();
        Deque<Coordinate> queue = new ArrayDeque<>();
        queue.add(start);
        while (!queue.isEmpty()) {
            Coordinate current = queue.getLast();
            if (current.equals(end)) {
                break;
            }

            boolean isMoved = false;

            for (int i = 0; i < DX.length; i++) {
                int nx = current.column() + DX[i];
                int ny = current.row() + DY[i];
                Coordinate next = new Coordinate(ny, nx);
                if (maze.getGrid()[ny][nx].type() == Type.PASSAGE
                    && !visitedCells.contains(next) && !queue.contains(next)) {
                    visitedCells.add(next);
                    queue.add(next);
                    isMoved = true;
                    break;
                }
            }
            if (!isMoved) {
                visitedCells.add(current);
                queue.pollLast();
            }
        }
        return queue.stream().toList();
    }
}
