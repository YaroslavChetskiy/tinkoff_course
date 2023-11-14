package edu.hw6.task5;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.net.http.HttpResponse.BodyHandlers;

public class HackerNews {

    private static final String ENDPOINTS_PARENT_URL = "https://hacker-news.firebaseio.com/v0/";

    private static final String TITLE_REGEX = "\"title\":\"(.+?)\"";
    public static final String HEADER_NAME = "Accept";
    public static final String HEADER_VALUE = "application/json";
    public static final String EMPTY_LINE = "";

    private final HttpClient client;

    public HackerNews(HttpClient client) {
        this.client = client;
    }

    public long[] hackerNewsTopStories() {
        try {
            var request = HttpRequest.newBuilder()
                .uri(new URI(ENDPOINTS_PARENT_URL + "topstories.json"))
                .GET()
                .header(HEADER_NAME, HEADER_VALUE)
                .build();
            var response = client.send(request, BodyHandlers.ofString());
            return Arrays.stream(response.body()
                    .substring(1, response.body().length() - 1)
                    .split(","))
                .mapToLong(Long::parseLong)
                .toArray();
        } catch (URISyntaxException | InterruptedException | IOException e) {
            return new long[0];
        }
    }

    public String news(long id) {
        try {
            var request = HttpRequest.newBuilder()
                .uri(new URI(ENDPOINTS_PARENT_URL + "item/" + id + ".json"))
                .GET()
                .header(HEADER_NAME, HEADER_VALUE)
                .build();

            var response = client.send(request, BodyHandlers.ofString());
            if (response == null) {
                return EMPTY_LINE;
            }
            Pattern pattern = Pattern.compile(TITLE_REGEX);
            Matcher matcher = pattern.matcher(response.body());
            return matcher.find() ? matcher.group(1) : EMPTY_LINE;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            return EMPTY_LINE;
        }
    }
}
