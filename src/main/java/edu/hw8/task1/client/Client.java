package edu.hw8.task1.client;

import java.io.Closeable;

public interface Client extends Closeable {

    void start();

    byte[] waitResponse();

    void send(byte[] bytes);
}
