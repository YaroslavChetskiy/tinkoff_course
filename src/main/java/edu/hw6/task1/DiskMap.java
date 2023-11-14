package edu.hw6.task1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DiskMap implements Map<String, String> {

    private static final String DEFAULT_STORAGE_PATH = "src/main/resources/kv-storage/storage.txt";

    private static final String DELIMITER = ":";

    private static final int DEFAULT_THRESHOLD = 5;

    private final Path storagePath;

    private final Map<String, String> data;

    private final int threshold;

    private int ops;

    public DiskMap() {
        this(DEFAULT_STORAGE_PATH, DEFAULT_THRESHOLD);
    }

    public DiskMap(String path) {
        this(path, DEFAULT_THRESHOLD);
    }

    public DiskMap(String path, int threshold) {
        this.storagePath = Path.of(path);
        try {
            Files.newBufferedWriter(storagePath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
                .close();
            ops = 0;
            data = new LinkedHashMap<>();
            this.threshold = threshold;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return data.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return data.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return data.get(key);
    }

    @Nullable
    @Override
    public String put(String key, String value) {
        var removedEntry = remove(key);
        data.put(key, value);
        ops++;
        if (ops % threshold == 0) {
            writeToFile();
        }
        return removedEntry;
    }

    @Override
    public String remove(Object key) {
        var removed = data.remove(key);
        if (removed != null) {
            ops++;
            if (ops % threshold == 0) {
                writeToFile();
            }
        }
        return removed;
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends String> m) {
        for (var entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        ops = 0;
        data.clear();
        try {
            Files.newBufferedWriter(storagePath, StandardOpenOption.TRUNCATE_EXISTING).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    @Override
    public Set<String> keySet() {
        return data.keySet();
    }

    @NotNull
    @Override
    public Collection<String> values() {
        return data.values();
    }

    @NotNull
    @Override
    public Set<Entry<String, String>> entrySet() {
        return data.entrySet();
    }

    private void writeToFile() {
        List<String> lines = data.entrySet()
            .stream()
            .map(entry -> entry.getKey() + DELIMITER + entry.getValue())
            .toList();
        try {
            Files.write(storagePath, lines, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
