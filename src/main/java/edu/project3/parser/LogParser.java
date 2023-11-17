package edu.project3.parser;

import edu.project3.exception.LogParserException;
import edu.project3.log.LogRecord;
import edu.project3.log.RequestRecord;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LogParser {
    private static final String LOG_FORMAT_REGEX =
        "^(.+?) - (\\S+?) "
            + "(\\[\\d{2}/[a-zA-Z]+/\\d{4}:\\d{2}:\\d{2}:\\d{2} \\+\\d{4}]) "
            + "(\"(GET|HEAD|POST|PUT|DELETE|CONNECT|OPTIONS|TRACE|PATCH) ((/.+)+) (HTTP/(\\d\\.\\d))\") "
            + "(\\d{3}) (\\d+) (\".*?\") (\".*?\")$";

    private static final String DATE_PATTERN = "dd/LLL/yyyy:HH:mm:ss Z";

    private static final int REMOTE_ADDR_GROUP = 1;
    private static final int REMOTE_USER_GROUP = 2;
    private static final int LOCAL_TIME_GROUP = 3;
    private static final int REQUEST_METHOD_GROUP = 5;
    private static final int REQUEST_RESOURCE_GROUP = 6;
    private static final int REQUEST_HTTP_VERSION_GROUP = 8;
    private static final int STATUS_GROUP = 10;
    private static final int BODY_BYTES_SENT_GROUP = 11;
    private static final int HTTP_REFERER_GROUP = 12;
    private static final int HTTP_USER_AGENT_GROUP = 13;

    private LogParser() {
    }

    public static LogRecord parse(String log) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.ENGLISH);
        Pattern pattern = Pattern.compile(LOG_FORMAT_REGEX);
        Matcher matcher = pattern.matcher(log);
        if (matcher.matches()) {
            var localTime = matcher.group(LOCAL_TIME_GROUP);
            return new LogRecord(
                matcher.group(REMOTE_ADDR_GROUP),
                matcher.group(REMOTE_USER_GROUP),
                OffsetDateTime.parse(localTime.substring(1, localTime.length() - 1), dateTimeFormatter),
                new RequestRecord(
                    matcher.group(REQUEST_METHOD_GROUP),
                    matcher.group(REQUEST_RESOURCE_GROUP),
                    matcher.group(REQUEST_HTTP_VERSION_GROUP)
                ),
                Integer.parseInt(matcher.group(STATUS_GROUP)),
                Long.parseLong(matcher.group(BODY_BYTES_SENT_GROUP)),
                matcher.group(HTTP_REFERER_GROUP),
                matcher.group(HTTP_USER_AGENT_GROUP)
            );
        } else {
            throw new LogParserException("Invalid log format");
        }
    }

    public static List<LogRecord> parseAll(List<String> log) {
        return log.stream()
            .map(LogParser::parse)
            .toList();
    }
}
