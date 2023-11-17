package edu.project3.reader;

import edu.project3.exception.LogReaderException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class LogReader {

    private static final String URL_REGEX = "^https?://(.+\\.\\w+)((:\\d{2,5})?(/.*).*)?$";
    private static final String GLOB_SYMBOL = "*";

    private LogReader() {
    }

    public static List<Path> getLogFiles(String path) {
        if (!path.contains(GLOB_SYMBOL)) {
            return List.of(Path.of(path));
        }

        List<Path> files = new ArrayList<>();

        int globStartIndex = path.indexOf('*');
        int lastSlashIndex = path.lastIndexOf('/');
        Path dir = Path.of(path.substring(0, Math.min(lastSlashIndex, globStartIndex)));
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + path);

        try {
            Files.walkFileTree(dir, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (pathMatcher.matches(file)) {
                        files.add(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new LogReaderException("Could not get log files in path: " + path, e);
        }
        return files;
    }

    public static List<String> getLogListByPath(String path) {
        if (path.matches(URL_REGEX)) {
            return getLogListFromURL(path);
        }
        return getLogListFromFiles(path);
    }

    private static List<String> getLogListFromFiles(String path) {
        List<String> logs = new ArrayList<>();
        var logFiles = getLogFiles(path);
        for (Path file : logFiles) {
            logs.addAll(getLogListFromFile(file));
        }
        return logs;
    }

    private static List<String> getLogListFromFile(Path filePath) {
        try (var linesStream = Files.lines(filePath)) {
            return linesStream.toList();
        } catch (IOException e) {
            throw new LogReaderException("Could not open file: " + filePath, e);
        }
    }

    private static List<String> getLogListFromURL(String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            var request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .header("Accept", "text/plain")
                .build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return Arrays.stream(response.body().split("\n")).toList();
        } catch (URISyntaxException | InterruptedException | IOException e) {
            throw new LogReaderException("Could not get logs from: " + url, e);
        }
    }
}
