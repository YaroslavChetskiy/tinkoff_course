package edu.hw5.task1;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class Task1 {

    private static final String DATE_PATTERN = "yyyy-MM-dd, HH:mm";
    public static final String DELIMITER = " - ";

    private static final String DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}, \\d{2}:\\d{2}";

    private Task1() {
    }

    public static Duration getAverageSessionTime(List<String> sessionsTime) {
        if (sessionsTime == null || sessionsTime.isEmpty()
            || !sessionsTime.stream()
            .allMatch(str -> str.matches(DATE_REGEX + DELIMITER + DATE_REGEX))) {
            throw new IllegalArgumentException("Invalid sessions time");
        }

        var dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);

        return sessionsTime.stream()
            .map(str -> {
                    String[] session = str.split(DELIMITER);
                    LocalDateTime start = LocalDateTime.parse(session[0], dateTimeFormatter);
                    LocalDateTime end = LocalDateTime.parse(session[1], dateTimeFormatter);
                    return Duration.between(start, end);
                }
            )
            .reduce(
                Duration.ZERO,
                (acc, duration) -> acc.plus(duration.dividedBy(sessionsTime.size()))
            );
    }
}
