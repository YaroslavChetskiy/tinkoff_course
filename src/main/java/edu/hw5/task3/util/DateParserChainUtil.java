package edu.hw5.task3.util;

import edu.hw5.task3.parser.DateParser;

public final class DateParserChainUtil {

    private DateParserChainUtil() {
    }

    public static DateParser buildChain(DateParser... parsers) {
        DateParser temp = parsers[0];
        for (int i = 1; i < parsers.length; i++) {
            temp.setNextDateParser(parsers[i]);
            temp = parsers[i];
        }
        return parsers[0];
    }
}
