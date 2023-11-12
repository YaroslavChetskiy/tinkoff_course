package edu.hw5.task3.parser;

import java.time.LocalDate;
import java.util.Optional;

public abstract class DateParser {

    protected DateParser nextDateParser;

    public void setNextDateParser(DateParser nextDateParser) {
        this.nextDateParser = nextDateParser;
    }

    public abstract Optional<LocalDate> parse(String date);

    public Optional<LocalDate> parseNext(String date) {
        return nextDateParser == null
            ? Optional.empty()
            : nextDateParser.parse(date);
    }
}
