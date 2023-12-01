package edu.hw8.task1.settings;

public record ServerSettings(String name, int port, int capacity) {

    public static final String DEFAULT_SERVER_NAME = "localhost";

    public static final int DEFAULT_PORT = 8081;

    public static final int CLIENT_MESSAGE_CAPACITY = 512;

    public ServerSettings() {
        this(DEFAULT_SERVER_NAME, DEFAULT_PORT, CLIENT_MESSAGE_CAPACITY);
    }
}
