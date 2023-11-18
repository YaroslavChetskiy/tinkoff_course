package edu.project3.exception;

public class ReportMakerException extends RuntimeException {

    public ReportMakerException(String message) {
        super(message);
    }

    public ReportMakerException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
