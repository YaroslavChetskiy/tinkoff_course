package edu.hw3.task8;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BackwardIteratorTest {

    @ParameterizedTest
    @DisplayName("Итерирование по различным коллекциям")
    @MethodSource("getArgumentsForBackwardIteratorTest")
    void testBackwardIteratorWithDifferentCollections(Collection<Integer> collection) {
        for (int el = 1; el <= 10; el++) {
            collection.add(el);
        }
        BackwardIterator<Integer> backwardIterator = new BackwardIterator<>(collection);
        for (int el = 10; el > 0; el--) {
            assertThat(backwardIterator.hasNext()).isTrue();
            assertThat(backwardIterator.next()).isEqualTo(el);
        }
        assertThat(backwardIterator.hasNext()).isFalse();
        assertThrows(NoSuchElementException.class, backwardIterator::next);
    }

    static Stream<Arguments> getArgumentsForBackwardIteratorTest() {
        return Stream.of(
            Arguments.of(new HashSet<Integer>()),
            Arguments.of(new ArrayList<Integer>()),
            Arguments.of(new LinkedList<Integer>()),
            Arguments.of(new ArrayDeque<Integer>())
        );
    }

}
