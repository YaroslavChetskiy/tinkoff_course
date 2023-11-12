package edu.hw6.task5;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class HackerNewsTest {

    private final HttpClient client = mock(HttpClient.class);

    private final HttpResponse response = mock(HttpResponse.class);

    private final HackerNews hackerNews = new HackerNews(client);

    @Test
    @DisplayName("Проверка получения идентификаторов статей")
    void getNewsStoriesIdsWithCorrectRequest() throws IOException, InterruptedException {
        doReturn("[1,202,404,503]").when(response).body();
        doReturn(response).when(client).send(any(), any());

        var actualResult = hackerNews.hackerNewsTopStories();
        assertThat(actualResult).isEqualTo(new long[] {1L, 202L, 404L, 503L});
    }

    @ParameterizedTest
    @DisplayName("Проверка получения названия статей")
    @MethodSource("getArgumentsForGetNewsStoryTitleTest")
    void getNewsStoryTitle(String responseBody, Long id, String expectedResult)
        throws IOException, InterruptedException {
        doReturn(responseBody).when(response).body();
        doReturn(response).when(client).send(any(), any());

        var actualResult = hackerNews.news(id);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForGetNewsStoryTitleTest() {
        return Stream.of(
            Arguments.of("""
                {
                	"by":"mfiguiere",
                	"descendants":327,
                	"id":123456,
                	"kids":[],
                	"score":246,
                	"time":1695132006,
                	"title":"Test title",
                	"type":"story",
                	"url":""
                }
                """, 123456L, "Test title"),
            Arguments.of("", 123L, "")
        );
    }

    @Test
    @DisplayName("Получение пустой строки при null ответе")
    void getEmptyStringIfResponseIsNull() throws IOException, InterruptedException {
        doReturn(null).when(client).send(any(), any());
        var actualResult = hackerNews.news(123L);
        assertThat(actualResult).isBlank();
    }
}
