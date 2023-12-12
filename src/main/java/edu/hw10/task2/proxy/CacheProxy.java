package edu.hw10.task2.proxy;

import edu.hw10.task2.handler.CacheInvocationHandler;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;

public final class CacheProxy {

    private CacheProxy() {
    }

    public static <T> T create(T target, Class<? extends T> clazz, Path dir) {
        if (!Files.isDirectory(dir)) {
            throw new IllegalArgumentException("Path should be directory");
        }
        return (T) Proxy.newProxyInstance(
            clazz.getClassLoader(),
            clazz.getInterfaces(),
            new CacheInvocationHandler(target, dir)
        );
    }
}
