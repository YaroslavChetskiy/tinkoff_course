package edu.hw3.task5;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import static edu.hw3.task5.SortOrder.ASC;
import static edu.hw3.task5.SortOrder.DESC;
import static edu.hw3.task5.Task5.parseContacts;
import static org.assertj.core.api.Assertions.assertThat;

class Task5Test {

    @ParameterizedTest
    @DisplayName("Сортировка списка контактов")
    @MethodSource("getArgumentsForSortContactsTest")
    void testSortingContacts(List<String> fullNames, SortOrder order, List<PersonProfile> expectedResult) {
        var actualResult = parseContacts(fullNames, order);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForSortContactsTest() {
        return Stream.of(
            Arguments.of(
                List.of("John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes"),
                ASC,
                List.of(
                    new PersonProfile("Thomas", "Aquinas"),
                    new PersonProfile("Rene", "Descartes"),
                    new PersonProfile("David", "Hume"),
                    new PersonProfile("John", "Locke")
                )
            ),
            Arguments.of(
                List.of("Paul Erdos", "Leonhard Euler", "Carl Gauss"),
                DESC,
                List.of(
                    new PersonProfile("Carl", "Gauss"),
                    new PersonProfile("Leonhard", "Euler"),
                    new PersonProfile("Paul", "Erdos")
                )
            ),
            Arguments.of(
                List.of("Paul", "Leonhard", "Carl"),
                ASC,
                List.of(
                    new PersonProfile("Carl", null),
                    new PersonProfile("Leonhard", null),
                    new PersonProfile("Paul", null)
                )
            ),
            Arguments.of(
                List.of("John", "Thomas", "David", "Rene"),
                DESC,
                List.of(
                    new PersonProfile("Thomas", null),
                    new PersonProfile("Rene", null),
                    new PersonProfile("John", null),
                    new PersonProfile("David", null)
                )
            ),
            Arguments.of(
                List.of("Amanda Key", "Simon", "George Bane", "Anny"),
                ASC,
                List.of(
                    new PersonProfile("George", "Bane"),
                    new PersonProfile("Amanda", "Key"),
                    new PersonProfile("Anny", null),
                    new PersonProfile("Simon", null)
                )
            ),
            Arguments.of(
                List.of(),
                DESC,
                List.of()
            ),
            Arguments.of(
                null,
                ASC,
                List.of()
            )
        );
    }

}
