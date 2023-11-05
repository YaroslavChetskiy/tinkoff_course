package edu.project2.solver;

import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import edu.project2.maze.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BfsSolver extends AbstractMazeSolver {

    private static final BfsSolver INSTANCE = new BfsSolver();

    private BfsSolver() {
    }

    public static BfsSolver getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        validateStartAndEndCells(start, end, maze);

        Map<Coordinate, List<Coordinate>> coordinatesMap = new HashMap<>();
        Deque<Coordinate> queue = new ArrayDeque<>();
        queue.add(start);
        coordinatesMap.put(start, new ArrayList<>(List.of(start)));
        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();

            if (current.equals(end)) {
                break;
            }

            for (int i = 0; i < DX.length; i++) {
                int nx = current.column() + DX[i];
                int ny = current.row() + DY[i];
                Coordinate next = new Coordinate(ny, nx);
                if (maze.getGrid()[ny][nx].type() == Type.PASSAGE && !coordinatesMap.containsKey(next)) {
                    coordinatesMap.put(next, new ArrayList<>(coordinatesMap.get(current)));
                    coordinatesMap.get(next).add(next);
                    queue.add(next);
                }
            }
        }
        return coordinatesMap.get(end);
    }
}
