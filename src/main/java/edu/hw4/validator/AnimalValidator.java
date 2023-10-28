package edu.hw4.validator;

import edu.hw4.Animal;
import static java.util.Objects.isNull;

public class AnimalValidator implements Validator<Animal> {

    private static final AnimalValidator INSTANCE = new AnimalValidator();

    private AnimalValidator() {
    }

    @Override
    public ValidationResult isValid(Animal object) {
        var validationResult = new ValidationResult();
        if (isNull(object.name()) || object.name().isBlank()) {
            validationResult.add(new ValidationError("name", "Name is invalid"));
        }
        if (isNull(object.type())) {
            validationResult.add(new ValidationError("type", "Type is invalid"));
        }
        if (isNull(object.sex())) {
            validationResult.add(new ValidationError("sex", "Sex is invalid"));
        }
        if (object.age() <= 0) {
            validationResult.add(new ValidationError("age", "Age is invalid"));
        }
        if (object.height() <= 0) {
            validationResult.add(new ValidationError("height", "Height is invalid"));
        }
        if (object.weight() <= 0) {
            validationResult.add(new ValidationError("weight", "Weight is invalid"));
        }
        return validationResult;
    }

    public static AnimalValidator getInstance() {
        return INSTANCE;
    }
}
