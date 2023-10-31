package edu.hw4.animal;

import edu.hw4.validator.AnimalValidator;
import edu.hw4.validator.ValidationResult;

public record Animal(
    String name,
    Type type,
    Sex sex,
    int age,
    int height,
    int weight,
    boolean bites
) {

    public int paws() {
        return type().getPaws();
    }

    public ValidationResult validate() {
        return AnimalValidator.getInstance().isValid(this);
    }
}
