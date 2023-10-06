package edu.hw1;

public final class Task1 {

    private static final int SECONDS_PER_MINUTE = 60;

    private Task1() {
    }

    public static int minutesToSeconds(String videoLength) {
        if (videoLength == null) {
            throw new NullPointerException();
        }

        if (!videoLength.matches("^\\d{2,}:\\d{2}$")) {
            throw new IllegalArgumentException("Invalid format");
        }

        String[] groups = videoLength.split(":");

        int minutes = Integer.parseInt(groups[0]);
        int seconds = Integer.parseInt(groups[1]);
        if (seconds < 0 || minutes < 0 || seconds >= SECONDS_PER_MINUTE) {
            return -1;
        }
        return minutes * SECONDS_PER_MINUTE + seconds;
    }
}
