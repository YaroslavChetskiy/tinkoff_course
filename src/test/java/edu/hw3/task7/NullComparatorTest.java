package edu.hw3.task7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.TreeMap;
import static org.assertj.core.api.Assertions.assertThat;

class NullComparatorTest {

    @Test
    @DisplayName("Добавление null в TreeMap с использованием NullComparator")
    void addNullToTreeMap() {
        Map<String, String> tree = new TreeMap<>(new NullComparator<>());
        tree.put("some key1", "some value1");
        tree.put("some key2", "some value2");
        tree.put(null, "test");
        tree.put("some key3", "some value3");

        assertThat(tree.containsKey(null)).isTrue();
        assertThat(tree.get(null)).isEqualTo("test");
    }

    @Test
    @DisplayName("Добавление нескольких null в TreeMap с использованием NullComparator")
    void addSomeNullToTreeMap() {
        Map<String, String> tree = new TreeMap<>(new NullComparator<>());
        tree.put("some key1", "some value1");
        tree.put("some key2", "some value2");
        tree.put(null, "test");
        tree.put("some key3", "some value3");
        tree.put(null, "dummy");

        assertThat(tree.containsKey(null)).isTrue();
        assertThat(tree.get(null)).isEqualTo("dummy");
    }

}
