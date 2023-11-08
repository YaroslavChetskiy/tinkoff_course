package edu.hw5.task3.parser;

import java.time.LocalDate;
import java.util.Optional;

public class RelativeDateParser extends DateParser {

    @Override
    public Optional<LocalDate> parse(String date) {
        if (date == null) {
            return Optional.empty();
        }
        return switch(date.toLowerCase()) {
            case "tomorrow" -> Optional.of(LocalDate.now().plusDays(1L));
            case "today" -> Optional.of(LocalDate.now());
            case "yesterday" -> Optional.of(LocalDate.now().minusDays(1L));
            default -> parseNext(date);
        };
    }
}
