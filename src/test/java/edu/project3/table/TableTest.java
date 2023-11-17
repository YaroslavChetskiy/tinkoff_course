package edu.project3.table;

import edu.project3.exception.TableException;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TableTest {

    @ParameterizedTest
    @DisplayName("Добавление правильной строки в таблицу")
    @MethodSource("getArgumentsForAddValidRowTest")
    void addValidRow(Table table, List<String> row) {
        table.addRow(row);
        assertThat(table.rows()).contains(row);
    }

    static Stream<Arguments> getArgumentsForAddValidRowTest() {
        return Stream.of(
            Arguments.of(
                new Table("Таблица", List.of("Title1", "Title2")),
                List.of("dummy", "dummy")
            ),
            Arguments.of(
                new Table("Таблица", List.of("Title1")),
                List.of("dummy")
            ),
            Arguments.of(
                new Table("Таблица", List.of("Title1", "Title2", "Title3")),
                List.of("dummy", "dummy", "dummy")
            )
        );
    }

    @Test
    @DisplayName("Выброс исключения при добавлении в таблицу строки некорректной длины")
    void throwTableExceptionIfRowHasInvalidColumnSize() {
        Table table = new Table("Table", List.of("Title1", "Title2", "Title3"));
        assertThrows(TableException.class, () -> table.addRow(List.of("1")));
        assertThrows(TableException.class, () -> table.addRow(List.of("1", "2")));
        assertThrows(TableException.class, () -> table.addRow(List.of("1", "2", "3", "4")));
    }
}
