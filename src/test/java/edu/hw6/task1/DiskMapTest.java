package edu.hw6.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

class DiskMapTest {

    private static final String NEW_TEST_MAP_PATH = "src/test/resources/hw6/kv-storage/test.txt";

    private static final Path DEFAULT_STORAGE_PATH = Path.of("src/main/resources/kv-storage/storage.txt");

    @Test
    @DisplayName("Создание хранилища по пути")
    void createStorageInPath() throws IOException {
        var diskMap = new DiskMap(NEW_TEST_MAP_PATH);
        var path = Path.of(NEW_TEST_MAP_PATH);
        assertThat(Files.exists(path)).isTrue();
        assertThat(Files.readAllBytes(path).length).isEqualTo(0);
        assertThat(diskMap.size()).isEqualTo(0);
        assertThat(diskMap.isEmpty()).isTrue();
        Files.delete(path);
    }

    @Test
    @DisplayName("Добавление в хранилище элементов")
    void addEntriesToStorage() throws IOException {
        var diskMap = new DiskMap();

        var entries = List.of(
            "key1:val1",
            "key2:val2",
            "key3:val3",
            "key4:val4",
            "key5:val5"
        );
        for (int i = 0; i < entries.size(); i++) {
            var splitEntry = entries.get(i).split(":");
            diskMap.put(splitEntry[0], splitEntry[1]);
            assertThat(diskMap.size()).isEqualTo(i + 1);
            assertThat(diskMap.containsKey(splitEntry[0])).isTrue();
            assertThat(diskMap.containsValue(splitEntry[1])).isTrue();
            assertThat(diskMap.get(splitEntry[0])).isEqualTo(splitEntry[1]);
        }
        var lines = Files.readAllLines(DEFAULT_STORAGE_PATH);
        assertThat(lines).isEqualTo(entries);
        diskMap.clear();

        Map<String, String> map = new LinkedHashMap<>();
        map.put("key1", "val1");
        map.put("key2", "val2");
        map.put("key3", "val3");
        map.put("key4", "val4");
        map.put("key5", "val5");
        diskMap.putAll(map);
        lines = Files.readAllLines(DEFAULT_STORAGE_PATH);
        assertThat(lines).isEqualTo(entries);
        diskMap.clear();
    }

    @Test
    @DisplayName("Очистка хранилища")
    void clearStorage() throws IOException {
        var diskMap = new DiskMap(DEFAULT_STORAGE_PATH.toString(), 1);
        diskMap.put("key1", "val1");
        diskMap.put("key2", "val2");
        diskMap.put("key3", "val3");
        diskMap.clear();
        assertThat(diskMap.size()).isEqualTo(0);
        assertThat(Files.readAllLines(DEFAULT_STORAGE_PATH)).isEmpty();
    }

    @Test
    @DisplayName("Удаление элементов")
    void removeEntries() throws IOException {
        var diskMap = new DiskMap(DEFAULT_STORAGE_PATH.toString());
        diskMap.putAll(Map.of(
                "key1", "val1",
                "key2", "val2",
                "key3", "val3"
            )
        );
        var removed = diskMap.remove("key1");
        assertThat(diskMap.get("key1")).isNull();
        assertThat(removed).isEqualTo("val1");

        removed = diskMap.remove("key2");
        assertThat(diskMap.get("key2")).isNull();
        assertThat(removed).isEqualTo("val2");

        removed = diskMap.remove("key4");
        assertThat(removed).isNull();

        var lines = Files.readAllLines(DEFAULT_STORAGE_PATH);
        assertThat(lines).isEqualTo(List.of("key3:val3"));
        diskMap.clear();
    }

    @Test
    @DisplayName("Получение коллекций")
    void getCollections() {
        var diskMap = new DiskMap();
        diskMap.put("key1", "val1");
        diskMap.put("key2", "val2");
        diskMap.put("key3", "val3");
        var keySet = diskMap.keySet();
        var values = diskMap.values();
        var entrySet = diskMap.entrySet();

        var map = Map.of(
            "key1", "val1",
            "key2", "val2",
            "key3", "val3"
        );
        assertThat(keySet).containsAll(map.keySet());
        assertThat(keySet.size()).isEqualTo(map.keySet().size());

        assertThat(values).containsAll(map.values());
        assertThat(values.size()).isEqualTo(map.values().size());

        assertThat(entrySet).containsAll(map.entrySet());
        assertThat(entrySet.size()).isEqualTo(map.entrySet().size());
    }
}
