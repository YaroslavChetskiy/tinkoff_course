package edu.hw9.task3.solver;

import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import edu.project2.maze.Type;
import edu.project2.solver.AbstractMazeSolver;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class MultiThreadDfsSolver extends AbstractMazeSolver {

    private final int numberOfThreads;

    public MultiThreadDfsSolver(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        validateStartAndEndCells(start, end, maze);

        ForkJoinPool forkJoinPool = new ForkJoinPool(numberOfThreads);
        Set<Coordinate> visitedCells = new HashSet<>();
        var resultPath = forkJoinPool.invoke(new DfsTask(maze, start, end, visitedCells));
        forkJoinPool.shutdown();
        return resultPath;
    }

    private static class DfsTask extends RecursiveTask<List<Coordinate>> {

        private final Maze maze;

        private final Coordinate current;

        private final Coordinate end;

        private final Set<Coordinate> visitedCells;

        private DfsTask(Maze maze, Coordinate current, Coordinate end, Set<Coordinate> visitedCells) {
            this.maze = maze;
            this.current = current;
            this.end = end;
            this.visitedCells = visitedCells;
        }

        @Override
        protected List<Coordinate> compute() {
            if (current.equals(end)) {
                return List.of(current);
            }
            List<ForkJoinTask<List<Coordinate>>> subTasks = new ArrayList<>();
            List<Coordinate> resultList = new ArrayList<>();
            for (int i = 0; i < DX.length; i++) {
                int nx = current.column() + DX[i];
                int ny = current.row() + DY[i];
                Coordinate next = new Coordinate(ny, nx);
                if (maze.getGrid()[ny][nx].type() == Type.PASSAGE
                    && !visitedCells.contains(next)) {
                    visitedCells.add(next);
                    subTasks.add(new DfsTask(maze, next, end, visitedCells).fork());
                }
            }
            for (var subTask : subTasks) {
                var list = subTask.join();
                if (!list.isEmpty()) {
                    resultList.add(current);
                    resultList.addAll(list);
                    break;
                }
            }

            return resultList;
        }
    }
}
