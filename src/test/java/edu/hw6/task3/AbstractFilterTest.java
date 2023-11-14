package edu.hw6.task3;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import static edu.hw6.task3.AbstractFilter.globMatches;
import static edu.hw6.task3.AbstractFilter.largerThan;
import static edu.hw6.task3.AbstractFilter.magicNumber;
import static edu.hw6.task3.AbstractFilter.readable;
import static edu.hw6.task3.AbstractFilter.regexContains;
import static edu.hw6.task3.AbstractFilter.writable;
import static org.junit.jupiter.api.Assertions.*;

class AbstractFilterTest {

    private static final Path TEST_DIR_PATH = Path.of("src", "test", "resources", "hw6", "task3");

    private static final AbstractFilter regularFile = Files::isRegularFile;

    @Test
    @DisplayName("Использование цепочки фильтров")
    void useFilterChain() throws IOException {

        DirectoryStream.Filter<Path> filter = regularFile
            .and(readable())
            .and(writable())
            .and(largerThan(100_000))
            .and(magicNumber((byte) 0x89, (byte) 'P', (byte) 'N', (byte) 'G'))
            .and(globMatches("*.png"))
            .and(regexContains("[-]"));

        try (var entries = Files.newDirectoryStream(TEST_DIR_PATH, filter)) {
            Assertions.assertThat(entries).containsExactly(TEST_DIR_PATH.resolve("tinkoff-one.png"));
        }
    }
}
