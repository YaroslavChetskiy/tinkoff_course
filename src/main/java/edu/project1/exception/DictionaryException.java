package edu.project1.exception;

public class DictionaryException extends RuntimeException {

    public DictionaryException(String message) {
        super(message);
    }

    public DictionaryException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
