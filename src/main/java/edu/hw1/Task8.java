package edu.hw1;

public final class Task8 {

    private static final int SIDE_LENGTH = 8;
    private static final int SMALL_SHIFT = 1;
    private static final int BIG_SHIFT = 2;

    private Task8() {
    }

    public static boolean knightBoardCapture(int[][] board) {
        int[] horizontalShifts = {
            SMALL_SHIFT, -SMALL_SHIFT,
            SMALL_SHIFT, -SMALL_SHIFT,
            BIG_SHIFT, BIG_SHIFT,
            -BIG_SHIFT, -BIG_SHIFT
        };
        int[] verticalShifts = {
            BIG_SHIFT, BIG_SHIFT,
            -BIG_SHIFT, -BIG_SHIFT,
            SMALL_SHIFT, -SMALL_SHIFT,
            SMALL_SHIFT, -SMALL_SHIFT
        };
        for (int y = 0; y < SIDE_LENGTH; y++) {
            for (int x = 0; x < SIDE_LENGTH; x++) {
                if (board[y][x] == 1) {
                    for (int i = 0; i < horizontalShifts.length; i++) {
                        int newX = x + horizontalShifts[i];
                        int newY = y + verticalShifts[i];
                        if (newX >= 0 && newX < SIDE_LENGTH && newY >= 0 && newY < SIDE_LENGTH
                            && board[newY][newX] == 1) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
