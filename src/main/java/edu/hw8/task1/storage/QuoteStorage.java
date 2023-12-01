package edu.hw8.task1.storage;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class QuoteStorage {

    private static final List<String> DEFAULT_QUOTES = List.of(
        "Не переходи на личности там, где их нет.",
        "Если твои противники перешли на личные оскорбления, будь уверена - твоя победа не за горами.",
        "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма.",
        "Чем ниже интеллект, тем громче оскорбления."
    );
    private final List<String> quotes;

    public QuoteStorage() {
        this(DEFAULT_QUOTES);
    }

    public QuoteStorage(List<String> quotes) {
        this.quotes = new CopyOnWriteArrayList<>(quotes);
    }

    public void addQuote(String quote) {
        if (!quotes.contains(quote)) {
            quotes.add(quote);
        }
    }

    public String getQuoteByKeyWord(String key) {
        return quotes.stream()
            .filter(quote -> quote.toLowerCase().contains(key.toLowerCase()))
            .findAny()
            .orElse("");
    }

}
