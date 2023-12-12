package edu.hw10.task2.handler;

import edu.hw10.task2.annotation.Cache;
import edu.hw10.task2.exception.CacheInvocationHandlerException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheInvocationHandler implements InvocationHandler {

    private static final String CACHE_FILE_EXTENSION = ".map";
    private final Object target;

    private final Path cacheFilePath;

    private final Map<String, Map<List<Object>, Object>> inMemoryCache = new HashMap<>();

    public CacheInvocationHandler(Object target, Path dir) {
        this.target = target;
        this.cacheFilePath = dir.resolve(target.getClass().toString() + CACHE_FILE_EXTENSION);
        var methods = target.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Cache.class)) {
                inMemoryCache.putIfAbsent(method.getName(), new HashMap<>());
            }
        }
        if (Files.exists(cacheFilePath)) {
            loadCache();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        var cacheAnnotation = method.getAnnotation(Cache.class);
        var argsList = Arrays.stream(args).toList();
        if (cacheAnnotation != null) {
            var argsMap = inMemoryCache.get(method.getName());
            if (argsMap.containsKey(argsList)) {
                return argsMap.get(argsList);
            }

            var result = method.invoke(target, args);
            argsMap.put(argsList, result);

            if (cacheAnnotation.persist()) {
                saveCacheToDisk();
            }

            return result;
        }
        return method.invoke(target, args);
    }

    private void saveCacheToDisk() {
        Map<String, Map<List<Object>, Object>> mapToSave = new HashMap<>();
        var methods = target.getClass().getDeclaredMethods();
        for (Method method : methods) {
            var cacheAnnotation = method.getAnnotation(Cache.class);
            if (cacheAnnotation != null && cacheAnnotation.persist()) {
                mapToSave.put(method.getName(), inMemoryCache.get(method.getName()));
            }
        }

        if (!mapToSave.isEmpty()) {
            try (ObjectOutputStream outputStream = new ObjectOutputStream(
                    new FileOutputStream(cacheFilePath.toFile())
                )) {
                outputStream.writeObject(mapToSave);
            } catch (IOException e) {
                throw new CacheInvocationHandlerException("Could not write into cache file", e);
            }
        }
    }

    private void loadCache() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(cacheFilePath.toFile()))) {
            try {
                var map = (Map<String, Map<List<Object>, Object>>) inputStream.readObject();
                inMemoryCache.putAll(map);
            } catch (ClassCastException e) {
                throw new CacheInvocationHandlerException("Invalid cache file data", e);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new CacheInvocationHandlerException("Could not read cache file", e);
        }
    }
}
