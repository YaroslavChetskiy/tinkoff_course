package edu.hw5.task2;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public final class Task2 {

    public static final int FRIDAY_NUMBER = 13;

    private Task2() {
    }

    public static List<LocalDate> getAllFriday13thOfYear(int year) {
        LocalDate yearBeginning = LocalDate.of(year, 1, 1);

        List<LocalDate> allFriday13th = new ArrayList<>();
        var nextFriday13th = getNextFriday13th(yearBeginning);
        while (nextFriday13th.getYear() == year) {
            allFriday13th.add(nextFriday13th);
            nextFriday13th = getNextFriday13th(nextFriday13th);
        }

        return allFriday13th;
    }

    public static LocalDate getNextFriday13th(LocalDate date) {
        LocalDate nextFriday = date.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        while (nextFriday.getDayOfMonth() != FRIDAY_NUMBER) {
            nextFriday = nextFriday.plusWeeks(1L);
        }
        return nextFriday;
    }
}
