package edu.project2.renderer;

import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import edu.project2.maze.Type;
import java.util.List;
import java.util.Map;

public class MazeRenderer implements Renderer {

    private static final Character PATH_SYMBOL = '·';
    private static final Character END_LINE_SYMBOL = '\n';

    private static final MazeRenderer INSTANCE = new MazeRenderer();
    private static final Map<Type, Character> TYPE_TO_SYMBOLIC_MAP = Map.of(
        Type.PASSAGE, ' ',
        Type.WALL, '█'
    );

    private MazeRenderer() {
    }

    public static MazeRenderer getInstance() {
        return INSTANCE;
    }

    @Override

    public String render(Maze maze) {
        return render(maze, List.of());
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        if (path == null) {
            throw new IllegalArgumentException("Invalid path");
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int y = 0; y < maze.getHeight(); y++) {
            for (int x = 0; x < maze.getWidth(); x++) {
                var cell = maze.getGrid()[y][x];
                if (path.contains(cell.coordinate())) {
                    stringBuilder.append(PATH_SYMBOL);
                } else {
                    stringBuilder.append(TYPE_TO_SYMBOLIC_MAP.get(cell.type()));
                }
            }
            stringBuilder.append(END_LINE_SYMBOL);
        }
        return stringBuilder.toString();
    }
}
