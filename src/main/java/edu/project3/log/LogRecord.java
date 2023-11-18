package edu.project3.log;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public record LogRecord(String remoteAddress, String remoteUser,
                        OffsetDateTime localTime, RequestRecord request,
                        int status, long bodyBytesSent,
                        String httpReferer, String httpUserAgent) {

    public boolean isBetweenDates(LocalDate from, LocalDate to) {
        var localDate = localTime.toLocalDate();
        return (localDate.isAfter(from) && localDate.isBefore(to))
            || localDate.isEqual(from) || localDate.isEqual(to);
    }
}
