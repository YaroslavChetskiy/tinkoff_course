package edu.project3.exception;

public class ArgumentsParseException extends RuntimeException {

    public ArgumentsParseException(String message) {
        super(message);
    }

    public ArgumentsParseException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
