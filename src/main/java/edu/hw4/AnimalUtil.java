package edu.hw4;

import edu.hw4.Animal.Sex;
import edu.hw4.validator.ValidationError;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import static edu.hw4.Animal.Type.DOG;
import static edu.hw4.Animal.Type.FISH;
import static edu.hw4.Animal.Type.SPIDER;
import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

public final class AnimalUtil {

    public static final int HUNDRED = 100;

    private AnimalUtil() {
    }

    // Task 1
    public static List<Animal> sortByHeight(List<Animal> animals) {
        return animals.stream()
            .sorted(comparing(Animal::height))
            .toList();
    }

    // Task2
    public static List<Animal> sortByWeightReversedAndLimit(List<Animal> animals, int k) {
        return animals.stream()
            .sorted(comparing(Animal::weight, reverseOrder()))
            .limit(k)
            .toList();
    }

    // Task 3
    public static Map<Animal.Type, Long> getTypeCounts(List<Animal> animals) {
        return animals.stream()
            .collect(groupingBy(Animal::type, counting()));
    }

    // Task 4
    public static Animal getLongestNameAnimal(List<Animal> animals) {
        return animals.stream()
            .max(comparing(animal -> animal.name().length()))
            .orElse(null);
    }

    // Task5
    public static Sex getLargerInNumberAnimalSex(List<Animal> animals) {
        return animals.stream()
            // как вариант посчитать сначала мужские особи, потом женские и сравнить
            //.filter(animal -> animal.sex() == Sex.M)
            .collect(groupingBy(Animal::sex, counting()))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
    }

    // Task 6
    public static Map<Animal.Type, Animal> getMaxWeightAnimalsForType(List<Animal> animals) {
        return animals.stream()
            .collect(toMap(
                Animal::type,
                identity(),
                (animal, animal2) -> animal.weight() > animal2.weight() ? animal : animal2
            ));
    }

    // Task 7
    public static Animal getKthOldestAnimal(List<Animal> animals, int k) {
        return animals.stream()
            .sorted(comparing(Animal::age).reversed())
            .skip(k - 1)
            .findFirst()
            .orElse(null);
    }

    // Task 8
    public static Optional<Animal> getMaxWeightAndHeightLessThanKAnimal(List<Animal> animals, int k) {
        return animals.stream()
            .filter(animal -> animal.height() < k)
            .max(comparing(Animal::weight));
    }

    // Task 9
    public static Integer countPaws(List<Animal> animals) {
        return animals.stream()
            .mapToInt(Animal::paws)
            .sum();
    }

    // Task 10
    public static List<Animal> getAnimalsWithNotEqualAgeAndPawsCount(List<Animal> animals) {
        return animals.stream()
            .filter(animal -> animal.age() != animal.paws())
            .toList();
    }

    // Task 11
    public static List<Animal> getCanBitesAndHeightGreaterThanHundredCmAnimal(List<Animal> animals) {
        return animals.stream()
            .filter(animal -> animal.bites() && animal.height() > HUNDRED)
            .toList();
    }

    // Task 12
    public static Integer getWeightGreaterThanHeightAnimalsCount(List<Animal> animals) {
        return animals.stream()
            .filter(animal -> animal.weight() > animal.height())
            .toList() // чтобы получить Integer как в условии, если Long - то просто count()
            .size();
    }

    // Task 13
    public static List<Animal> getAnimalsWithMoreThanTwoWordsName(List<Animal> animals) {
        return animals.stream()
            .filter(animal -> animal.name().split(" +").length > 2)
            .toList();
    }

    // Task 14
    public static Boolean containsDogBiggerThanKCentimeters(List<Animal> animals, int k) {
        return animals.stream()
            .anyMatch(animal -> animal.type() == DOG && animal.height() > k);
    }

    // Task 15
    // обычно "до" - не включительно
    public static Integer getSummaryWeightOfAnimalsWithAgeBetween(List<Animal> animals, int k, int l) {
        return animals.stream()
            .filter(animal -> animal.age() >= k && animal.age() < l)
            .mapToInt(Animal::weight)
            .sum();
    }

    // Task 16
    public static List<Animal> sortByTypeThenSexThenName(List<Animal> animals) {
        return animals.stream()
            .sorted(
                comparing(Animal::type)
                    .thenComparing(Animal::sex)
                    .thenComparing(Animal::name))
            .toList();
    }

    // Task 17
    public static Boolean checkSpidersBiteOftenThanDogs(List<Animal> animals) {
        return animals.stream()
            .filter(animal -> animal.type() == SPIDER && animal.bites())
            .count()
            > animals.stream()
            .filter(animal -> animal.type() == DOG && animal.bites())
            .count();
    }

    // Task 18
    public static Animal getHeaviestFishInSeveralLists(List<List<Animal>> animalLists) {
        return animalLists.stream()
            .flatMap(Collection::stream)
            .filter(animal -> animal.type() == FISH)
            .max(comparing(Animal::weight))
            .orElse(null);
    }

    // Task 19
    public static Map<String, Set<ValidationError>> getAnimalsNamesWithValidationErrors(List<Animal> animals) {
        return animals.stream()
            .filter(animal -> !animal.validate().isValid())
            .collect(toMap(
                animal -> animal.name() == null ? "NoName" : animal.name(),
                animal -> animal.validate().getErrors()
            ));
    }

    // Task 20
    public static Map<String, String> getAnimalsNamesWithInvalidFieldNames(List<Animal> animals) {
        return getAnimalsNamesWithValidationErrors(animals)
            .entrySet().stream()
            .map(entry -> Map.entry(
                entry.getKey(),
                entry.getValue().stream()
                    .map(ValidationError::fieldName)
                    .collect(joining(", "))
            ))
            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
