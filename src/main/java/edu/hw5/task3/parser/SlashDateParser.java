package edu.hw5.task3.parser;

import java.time.LocalDate;
import java.util.Optional;

public class SlashDateParser extends DateParser {

    public static final int CENTURY_21 = 2000;
    private static final String DATE_REGEX = "\\d{1,2}/\\d{1,2}/(\\d{2}|\\d{4})";
    public static final int YEAR_BOUND = 100;
    public static final int CENTURY_20 = 1900;

    @Override
    public Optional<LocalDate> parse(String date) {
        if (date == null || !date.matches(DATE_REGEX)) {
            return parseNext(date);
        }
        var splitDate = date.split("/");
        var day = Integer.parseInt(splitDate[0]);
        var month = Integer.parseInt(splitDate[1]);
        var year = Integer.parseInt(splitDate[2]);
        if (year < YEAR_BOUND) {
            year = year + CENTURY_21 > LocalDate.now().getYear() ? year + CENTURY_20 : year + CENTURY_21;
        }
        return Optional.of(LocalDate.of(year, month, day));
    }
}
