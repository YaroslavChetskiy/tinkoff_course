package edu.hw4.validator;

import java.util.LinkedHashSet;
import java.util.Set;

public class ValidationResult {

    private final Set<ValidationError> errors = new LinkedHashSet<>();

    public Set<ValidationError> getErrors() {
        return errors;
    }

    public void add(ValidationError error) {
        this.errors.add(error);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }
}
