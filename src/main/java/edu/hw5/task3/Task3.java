package edu.hw5.task3;

import edu.hw5.task3.parser.HatchDateParser;
import edu.hw5.task3.parser.OffsetDateParser;
import edu.hw5.task3.parser.RelativeDateParser;
import edu.hw5.task3.parser.SlashDateParser;
import java.time.LocalDate;
import java.util.Optional;
import static edu.hw5.task3.util.DateParserChainUtil.buildChain;

public final class Task3 {

    private Task3() {
    }

    public static Optional<LocalDate> parseDate(String string) {
        return buildChain(
            new HatchDateParser(),
            new SlashDateParser(),
            new RelativeDateParser(),
            new OffsetDateParser()
        ).parse(string);
    }
}
