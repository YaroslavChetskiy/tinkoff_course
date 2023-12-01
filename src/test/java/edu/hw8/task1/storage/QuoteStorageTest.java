package edu.hw8.task1.storage;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

class QuoteStorageTest {

    @ParameterizedTest
    @DisplayName("Получение цитаты по ключу")
    @MethodSource("getArgumentsForGetCorrectQuoteByKeyWordTest")
    void getCorrectQuoteByKeyWord(String key, QuoteStorage quoteStorage, List<String> expectedResult) {
        var actualResult = quoteStorage.getQuoteByKeyWord(key);
        assertThat(actualResult).isIn(expectedResult);
    }

    static Stream<Arguments> getArgumentsForGetCorrectQuoteByKeyWordTest() {
        return Stream.of(
            Arguments.of(
                "оскорбления",
                new QuoteStorage(),
                List.of("Если твои противники перешли на личные оскорбления, будь уверена - твоя победа не за горами.")
            ),
            Arguments.of(
                "quote",
                new QuoteStorage(List.of("First quote", "Second quote", "Extra")),
                List.of("First quote", "Second quote")
            ),
            Arguments.of(
                "noExistingQuote",
                new QuoteStorage(),
                List.of("")
            ),
            Arguments.of(
                "ЛИЧНОСТИ",
                new QuoteStorage(),
                List.of("Не переходи на личности там, где их нет.")
            )
        );
    }

    @Test
    @DisplayName("Добавление новых цитат")
    void addNewQuotes() {
        var quotes = List.of("First quote", "Second quote", "Third quote");
        var storage = new QuoteStorage(List.of());
        for (String quote : quotes) {
            storage.addQuote(quote);
        }
        for (String quote : quotes) {
            var actualResult = storage.getQuoteByKeyWord(quote);
            assertThat(actualResult).isEqualTo(quote);
        }
    }

}
