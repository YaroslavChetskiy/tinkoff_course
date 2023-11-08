package edu.hw5.task3.parser;

import java.time.LocalDate;
import java.util.Optional;

public class OffsetDateParser extends DateParser {

    private static final String DATE_REGEX = "\\d+ (days?|weeks?|months?|years?) (ago|after)";

    @Override
    public Optional<LocalDate> parse(String date) {
        if (date == null || !date.matches(DATE_REGEX)) {
            return parseNext(date);
        }
        var splitDate = date.split(" ");
        boolean isFuture = splitDate[2].equals("after");
        long value = Long.parseLong(splitDate[0]) * (isFuture ? 1 : -1);
        LocalDate now = LocalDate.now();

        return switch (splitDate[1]) {
            case "day", "days" -> Optional.of(now.plusDays(value));
            case "week", "weeks" -> Optional.of(now.plusWeeks(value));
            case "month", "months" -> Optional.of(now.plusMonths(value));
            case "year", "years" -> Optional.of(now.plusYears(value));
            default -> parseNext(date);
        };
    }
}
