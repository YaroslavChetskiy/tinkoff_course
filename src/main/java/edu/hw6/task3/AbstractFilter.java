package edu.hw6.task3;

import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface AbstractFilter extends DirectoryStream.Filter<Path> {

    static AbstractFilter readable() {
        return Files::isReadable;
    }

    static AbstractFilter writable() {
        return Files::isWritable;
    }

    static AbstractFilter largerThan(long size) {
        return (path -> Files.size(path) > size);
    }

    static AbstractFilter magicNumber(byte... bytes) {
        return (path -> {
            var fileBytes = Files.readAllBytes(path);
            if (bytes.length > fileBytes.length) {
                return false;
            }
            for (int i = 0; i < bytes.length; i++) {
                if (fileBytes[i] != bytes[i]) {
                    return false;
                }
            }
            return true;
        });
    }

    static AbstractFilter globMatches(String glob) {
        return (path -> FileSystems
            .getDefault()
            .getPathMatcher("glob:" + glob)
            .matches(path.getFileName())
        );
    }

    static AbstractFilter regexContains(String regex) {
        return (path -> {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(path.getFileName().toString());
            return matcher.find();
        });
    }

    default AbstractFilter and(AbstractFilter filter) {
        return (path -> this.accept(path) && filter.accept(path));
    }
}
