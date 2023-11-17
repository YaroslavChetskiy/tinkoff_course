package edu.project3.parser;

import edu.project3.config.Config;
import edu.project3.exception.ArgumentsParseException;
import edu.project3.statistics.StatsOutputFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import static edu.project3.statistics.StatsOutputFormat.ADOC;

public final class ArgumentParser {

    private ArgumentParser() {
    }

    public static Config parse(String[] args) {
        if (args.length == 0) {
            throw new ArgumentsParseException("Empty options");
        }
        String path = "";
        LocalDate from = LocalDate.MIN;
        LocalDate to = LocalDate.MAX;
        StatsOutputFormat format = ADOC;
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--") && (i == args.length - 1 || args[i + 1].startsWith("--"))) {
                throw new ArgumentsParseException("Invalid option params: " + args[i]);
            }
            switch (args[i]) {
                case "--path" -> {
                    path = args[i + 1];
                }
                case "--from" -> {
                    from = getDate(args[i + 1]);
                }
                case "--to" -> {
                    to = getDate(args[i + 1]);
                }
                case "--format" -> {
                    var outputFormat = StatsOutputFormat.find(args[i + 1].toUpperCase());
                    if (outputFormat.isPresent()) {
                        format = outputFormat.get();
                    } else {
                        throw new ArgumentsParseException("Invalid output format");
                    }
                }
                default -> {
                    continue; // иначе checkstyle ругается
                }
            }
        }
        if (path.isBlank() && path.isBlank()) {
            throw new ArgumentsParseException("Invalid options: required --path option was not found");
        }
        return new Config(path, from, to, format);
    }

    private static LocalDate getDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new ArgumentsParseException("Invalid date format", e);
        }
    }

}
