package edu.hw5.task3.parser;

import java.time.LocalDate;
import java.util.Optional;

public class HatchDateParser extends DateParser {
    private static final String DATE_REGEX = "\\d{4}-\\d{2}-\\d{1,2}";

    @Override
    public Optional<LocalDate> parse(String date) {
        if (date == null || !date.matches(DATE_REGEX)) {
            return parseNext(date);
        }
        String[] splitDate = date.split("-");
        // думаю, что в этом случае только запись дней может быть разной длины,
        // т.к иначе это было бы слишком похоже на 2 способ указания даты
        splitDate[2] = splitDate[2].length() == 1 ?
            "0" + splitDate[2]
            : splitDate[2];
        return Optional.of(LocalDate.parse(String.join("-", splitDate)));
    }
}
