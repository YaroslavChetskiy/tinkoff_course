package edu.hw5.task3.parser;

import java.time.LocalDate;
import java.util.Optional;

public class SlashDateParser extends DateParser {

    private static final String DATE_REGEX = "\\d{1,2}/\\d{1,2}/(\\d{2}|\\d{4})";

    @Override
    public Optional<LocalDate> parse(String date) {
        if (date == null || !date.matches(DATE_REGEX)) {
            return parseNext(date);
        }
        var splitDate = date.split("/");
        var day = Integer.parseInt(splitDate[0]);
        var month = Integer.parseInt(splitDate[1]);
        var year = Integer.parseInt(splitDate[2]);
        if (year < 100) {
            year = year + 2000 > LocalDate.now().getYear() ? year + 1900 : year + 2000;
        }
        return Optional.of(LocalDate.of(year, month, day));
    }
}
