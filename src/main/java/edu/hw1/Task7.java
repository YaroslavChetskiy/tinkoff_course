package edu.hw1;

public final class Task7 {

    private Task7() {
    }
    // Ох уж эти побитовые операции... Надеюсь, не запутался

    public static int rotateLeft(int n, int shift) {
        String binary = Integer.toBinaryString(n);
        if (binary.length() - shift < 0) {
            return rotateLeft(n, shift % binary.length());
        }
        int part1 = (n << shift) & Integer.parseInt(binary.replace("0", "1"), 2);
        int part2 = n >> (binary.length() - shift);
        return part1 | part2;
    }

    public static int rotateRight(int n, int shift) {
        String binary = Integer.toBinaryString(n);
        if (binary.length() - shift < 0) {
            return rotateRight(n, shift % binary.length());
        }
        int part1 = n >> shift;
        int part2 = n << (binary.length() - shift) & Integer.parseInt(binary.replace("0", "1"), 2);
        return part1 | part2;
    }
}
