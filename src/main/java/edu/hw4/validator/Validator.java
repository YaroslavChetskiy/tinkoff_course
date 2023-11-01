package edu.hw4.validator;

public interface Validator<T> {
    ValidationResult isValid(T object);
}
