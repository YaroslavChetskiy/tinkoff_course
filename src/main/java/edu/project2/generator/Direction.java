package edu.project2.generator;

public enum Direction {
    NORTH(0, -2),
    SOUTH(0, 2),
    EAST(2, 0),
    WEST(-2, 0);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
