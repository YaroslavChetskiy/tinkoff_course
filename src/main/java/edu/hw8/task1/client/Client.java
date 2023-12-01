package edu.hw8.task1.client;

import java.io.Closeable;
import java.util.Optional;

public interface Client extends Closeable {

    void start();

    Optional<byte[]> waitResponse();

    void send(byte[] bytes);
}
