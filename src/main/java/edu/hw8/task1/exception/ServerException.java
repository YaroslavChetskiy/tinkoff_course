package edu.hw8.task1.exception;

public class ServerException extends RuntimeException {

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
