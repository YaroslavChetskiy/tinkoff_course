package edu.hw4;

import edu.hw4.Animal.Sex;
import edu.hw4.Animal.Type;
import edu.hw4.validator.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import static edu.hw4.Animal.Sex.F;
import static edu.hw4.Animal.Sex.M;
import static edu.hw4.AnimalUtil.checkSpidersBiteOftenThanDogs;
import static edu.hw4.AnimalUtil.containsDogBiggerThanKCentimeters;
import static edu.hw4.AnimalUtil.countPaws;
import static edu.hw4.AnimalUtil.getAnimalsNamesWithInvalidFieldNames;
import static edu.hw4.AnimalUtil.getAnimalsNamesWithValidationErrors;
import static edu.hw4.AnimalUtil.getAnimalsWithMoreThanTwoWordsName;
import static edu.hw4.AnimalUtil.getAnimalsWithNotEqualAgeAndPawsCount;
import static edu.hw4.AnimalUtil.getCanBitesAndHeightGreaterThanHundredCmAnimal;
import static edu.hw4.AnimalUtil.getHeaviestFishInSeveralLists;
import static edu.hw4.AnimalUtil.getKthOldestAnimal;
import static edu.hw4.AnimalUtil.getLargerInNumberAnimalSex;
import static edu.hw4.AnimalUtil.getLongestNameAnimal;
import static edu.hw4.AnimalUtil.getMaxWeightAndHeightLessThanKAnimal;
import static edu.hw4.AnimalUtil.getMaxWeightAnimalsForType;
import static edu.hw4.AnimalUtil.getSummaryWeightOfAnimalsWithAgeBetween;
import static edu.hw4.AnimalUtil.getTypeCounts;
import static edu.hw4.AnimalUtil.getWeightGreaterThanHeightAnimalsCount;
import static edu.hw4.AnimalUtil.sortByHeight;
import static edu.hw4.AnimalUtil.sortByTypeThenSexThenName;
import static edu.hw4.AnimalUtil.sortByWeightReversedAndLimit;
import static org.assertj.core.api.Assertions.assertThat;

class AnimalUtilTest {
    // CAT, DOG, BIRD, FISH, SPIDER
    private static final Animal CAT = new Animal("Barsik", Type.CAT, M, 5, 45, 4000, true);
    private static final Animal BIG_CAT = new Animal("Moon", Type.CAT, F, 8, 145, 8000, true);
    private static final Animal DOG = new Animal("Bobik", Type.DOG, F, 4, 50, 13000, false);
    private static final Animal DOG2 = new Animal("Sharik", Type.DOG, M, 6, 44, 16000, true);
    private static final Animal BIRD = new Animal("Pigeon", Type.BIRD, F, 8, 40, 300, false);
    private static final Animal LIGHT_BIRD = new Animal("Pidgeot", Type.BIRD, F, 3, 60, 30, true);
    private static final Animal FISH = new Animal("Salivan", Type.FISH, F, 3, 10, 2500, false);
    private static final Animal LIGHT_FISH = new Animal("Salivanio", Type.FISH, M, 4, 8, 1500, false);
    private static final Animal HEAVY_FISH = new Animal("Salivana", Type.FISH, F, 5, 15, 4000, true);
    private static final Animal SPIDER = new Animal("Tarantula", Type.SPIDER, M, 1, 1, 175, true);
    private static final Animal SPLIT_NAME_SPIDER = new Animal("Mrs. Taran Tula ", Type.SPIDER, F, 1, 2, 200, false);

    // Task 1
    static Stream<Arguments> getArgumentsForSortByHeightTest() {
        return Stream.of(
            Arguments.of(List.of(), List.of()),
            Arguments.of(List.of(BIRD, CAT, FISH), List.of(FISH, BIRD, CAT)),
            Arguments.of(List.of(CAT, CAT, DOG, SPIDER), List.of(SPIDER, CAT, CAT, DOG)),
            Arguments.of(List.of(FISH, CAT, BIRD, SPIDER, DOG), List.of(SPIDER, FISH, BIRD, CAT, DOG))
        );
    }

    @ParameterizedTest
    @DisplayName("Сортировка по росту")
    @MethodSource("getArgumentsForSortByHeightTest")
    void testSortByHeight(List<Animal> animals, List<Animal> expectedResult) {
        var actualResult = sortByHeight(animals);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    // Task 2
    static Stream<Arguments> getArgumentsForSortByWeightReversedAndLimitTest() {
        return Stream.of(
            Arguments.of(List.of(), 1, List.of()),
            Arguments.of(List.of(BIRD, CAT, FISH), 2, List.of(CAT, FISH)),
            Arguments.of(List.of(BIRD, CAT, FISH), 0, List.of()),
            Arguments.of(List.of(CAT, CAT, DOG, SPIDER), 3, List.of(DOG, CAT, CAT)),
            Arguments.of(List.of(FISH, CAT, BIRD, SPIDER, DOG), 5, List.of(DOG, CAT, FISH, BIRD, SPIDER))
        );
    }

    @ParameterizedTest
    @DisplayName("Сортировка по весу с выбором первых k")
    @MethodSource("getArgumentsForSortByWeightReversedAndLimitTest")
    void testSortByWeightReversedAndLimit(List<Animal> animals, int k, List<Animal> expectedResult) {
        var actualResult = sortByWeightReversedAndLimit(animals, k);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    // Task 3
    static Stream<Arguments> getArgumentsForGetTypeCountsTest() {
        return Stream.of(
            Arguments.of(List.of(), Map.of()),
            Arguments.of(List.of(BIRD, CAT, FISH), Map.of(
                Type.BIRD, 1L,
                Type.CAT, 1L,
                Type.FISH, 1L
            )),
            Arguments.of(List.of(FISH, FISH, FISH), Map.of(Type.FISH, 3L)),
            Arguments.of(List.of(CAT, CAT, DOG, SPIDER), Map.of(
                Type.DOG, 1L,
                Type.CAT, 2L,
                Type.SPIDER, 1L
            )),
            Arguments.of(List.of(FISH, CAT, BIRD, SPIDER, DOG), Map.of(
                Type.FISH, 1L,
                Type.CAT, 1L,
                Type.BIRD, 1L,
                Type.SPIDER, 1L,
                Type.DOG, 1L
            ))
        );
    }

    @ParameterizedTest
    @DisplayName("Получение количества животных каждого типа")
    @MethodSource("getArgumentsForGetTypeCountsTest")
    void testGetTypeCounts(List<Animal> animals, Map<Type, Long> expectedResult) {
        var actualResult = getTypeCounts(animals);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    // Task 4
    static Stream<Arguments> getArgumentsForGetLongestNameAnimalTest() {
        return Stream.of(
            Arguments.of(List.of(), null),
            Arguments.of(List.of(BIRD, CAT, FISH), FISH),
            Arguments.of(List.of(BIRD, CAT), BIRD),
            Arguments.of(List.of(CAT, CAT, DOG, DOG), CAT),
            Arguments.of(List.of(FISH, CAT, BIRD, SPIDER, DOG), SPIDER)
        );
    }

    @ParameterizedTest
    @DisplayName("Получение животного с самым длинным именем")
    @MethodSource("getArgumentsForGetLongestNameAnimalTest")
    void testGetLongestNameAnimal(List<Animal> animals, Animal expectedResult) {
        var actualResult = getLongestNameAnimal(animals);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    // Task 5
    static Stream<Arguments> getArgumentsForGetLargerInNumberAnimalSexTest() {
        return Stream.of(
            Arguments.of(List.of(), null),
            Arguments.of(List.of(BIRD, CAT, FISH), F),
            Arguments.of(List.of(CAT, CAT, DOG, CAT, DOG), M),
            Arguments.of(List.of(FISH, CAT, BIRD, SPIDER, DOG), F)
        );
    }

    @ParameterizedTest
    @DisplayName("Получение самого частого пола")
    @MethodSource("getArgumentsForGetLargerInNumberAnimalSexTest")
    void testGetLargerInNumberAnimalSex(List<Animal> animals, Sex expectedResult) {
        var actualResult = getLargerInNumberAnimalSex(animals);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    // Task 6
    static Stream<Arguments> getArgumentsForGetMaxWeightAnimalsForTypeTest() {
        return Stream.of(
            Arguments.of(List.of(), Map.of()),
            Arguments.of(
                List.of(
                    BIRD,
                    new Animal("dummy", Type.BIRD, M, 5, 10, 250, false),
                    FISH
                ),
                Map.of(
                    Type.BIRD, BIRD,
                    Type.FISH, FISH
                )
            ),
            Arguments.of(List.of(FISH, FISH, FISH), Map.of(Type.FISH, FISH)),
            Arguments.of(
                List.of(CAT, CAT, DOG, DOG2, SPIDER),
                Map.of(
                    Type.DOG, DOG2,
                    Type.CAT, CAT,
                    Type.SPIDER, SPIDER
                )
            ),
            Arguments.of(
                List.of(FISH, CAT, BIRD, SPIDER, DOG),
                Map.of(
                    Type.FISH, FISH,
                    Type.CAT, CAT,
                    Type.BIRD, BIRD,
                    Type.SPIDER, SPIDER,
                    Type.DOG, DOG
                )
            )
        );
    }

    @ParameterizedTest
    @DisplayName("Получение самых тяжёлых животных каждого типа")
    @MethodSource("getArgumentsForGetMaxWeightAnimalsForTypeTest")
    void testGetMaxWeightAnimalsForType(List<Animal> animals, Map<Type, Animal> expectedResult) {
        var actualResult = getMaxWeightAnimalsForType(animals);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    // Task 7
    static Stream<Arguments> getArgumentsForGetKthOldestAnimalTest() {
        return Stream.of(
            Arguments.of(List.of(), 1, null),
            Arguments.of(List.of(BIRD, CAT, FISH), 4, null),
            Arguments.of(List.of(BIRD, CAT, FISH), 1, BIRD),
            Arguments.of(List.of(CAT, CAT, DOG, SPIDER), 2, CAT),
            Arguments.of(List.of(SPIDER, SPIDER, SPIDER), 3, SPIDER)
        );
    }

    @ParameterizedTest
    @DisplayName("Получение К-ого самого старого животного")
    @MethodSource("getArgumentsForGetKthOldestAnimalTest")
    void testGetKthOldestAnimal(List<Animal> animals, int k, Animal expectedResult) {
        var actualResult = getKthOldestAnimal(animals, k);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    // Task 8
    static Stream<Arguments> getArgumentsForGetMaxWeightAndHeightLessThanKAnimalTest() {
        return Stream.of(
            Arguments.of(List.of(), 50, Optional.empty()),
            Arguments.of(List.of(BIRD, CAT, FISH), 35, Optional.of(FISH)),
            Arguments.of(List.of(BIRD, CAT, FISH), 50, Optional.of(CAT)),
            Arguments.of(List.of(CAT, CAT, DOG2, SPIDER), 45, Optional.of(DOG2)),
            Arguments.of(List.of(SPIDER, SPIDER, SPIDER), 5, Optional.of(SPIDER)),
            Arguments.of(List.of(SPIDER, SPIDER, SPIDER), 1, Optional.empty())
        );
    }

    @ParameterizedTest
    @DisplayName("Получение самого тяжёлого животного среди тех, что ниже k см")
    @MethodSource("getArgumentsForGetMaxWeightAndHeightLessThanKAnimalTest")
    void testGetMaxWeightAndHeightLessThanKAnimal(List<Animal> animals, int k, Optional<Animal> expectedResult) {
        var actualResult = getMaxWeightAndHeightLessThanKAnimal(animals, k);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    // Task 9
    static Stream<Arguments> getArgumentsForCountPawsTest() {
        return Stream.of(
            Arguments.of(List.of(), 0),
            Arguments.of(List.of(BIRD, CAT, FISH), 6),
            Arguments.of(List.of(CAT, CAT, CAT), 12),
            Arguments.of(List.of(FISH, CAT, BIRD, SPIDER, DOG), 18)
        );
    }

    @ParameterizedTest
    @DisplayName("Подсчёт количества лап всех животных")
    @MethodSource("getArgumentsForCountPawsTest")
    void testCountPaws(List<Animal> animals, Integer expectedResult) {
        var actualResult = countPaws(animals);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    // Task 10
    static Stream<Arguments> getArgumentsForGetAnimalsWithNotEqualAgeAndPawsCountTest() {
        return Stream.of(
            Arguments.of(List.of(), List.of()),
            Arguments.of(List.of(BIRD, DOG, FISH), List.of(BIRD, FISH)),
            Arguments.of(List.of(DOG, DOG, DOG), List.of()),
            Arguments.of(List.of(SPIDER, SPIDER, SPIDER), List.of(SPIDER, SPIDER, SPIDER))
        );
    }

    @ParameterizedTest
    @DisplayName("Получение животных, чьи рост и количество лап не совпадают")
    @MethodSource("getArgumentsForGetAnimalsWithNotEqualAgeAndPawsCountTest")
    void testGetAnimalsWithNotEqualAgeAndPawsCount(List<Animal> animals, List<Animal> expectedResult) {
        var actualResult = getAnimalsWithNotEqualAgeAndPawsCount(animals);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    // Task 11
    static Stream<Arguments> getArgumentsForGetCanBitesAndHeightGreaterThanHundredCmAnimalTest() {
        return Stream.of(
            Arguments.of(List.of(), List.of()),
            Arguments.of(List.of(BIRD, DOG, FISH), List.of()),
            Arguments.of(List.of(DOG, BIG_CAT, SPIDER), List.of(BIG_CAT)),
            Arguments.of(List.of(BIG_CAT, BIG_CAT, BIG_CAT), List.of(BIG_CAT, BIG_CAT, BIG_CAT))
        );
    }

    @ParameterizedTest
    @DisplayName("Получение животных, которые могут укусить и выше 100 см")
    @MethodSource("getArgumentsForGetCanBitesAndHeightGreaterThanHundredCmAnimalTest")
    void testGetCanBitesAndHeightGreaterThanHundredCmAnimal(List<Animal> animals, List<Animal> expectedResult) {
        var actualResult = getCanBitesAndHeightGreaterThanHundredCmAnimal(animals);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    // Task 12
    static Stream<Arguments> getArgumentsForGetWeightGreaterThanHeightAnimalsCountTest() {
        return Stream.of(
            Arguments.of(List.of(), 0),
            Arguments.of(List.of(BIRD, DOG, FISH), 3),
            Arguments.of(List.of(DOG, LIGHT_BIRD, SPIDER), 2),
            Arguments.of(List.of(LIGHT_BIRD, LIGHT_BIRD, LIGHT_BIRD), 0)
        );
    }

    @ParameterizedTest
    @DisplayName("Подсчёт животный, чей вес больше роста")
    @MethodSource("getArgumentsForGetWeightGreaterThanHeightAnimalsCountTest")
    void testGetWeightGreaterThanHeightAnimalsCount(List<Animal> animals, Integer expectedResult) {
        var actualResult = getWeightGreaterThanHeightAnimalsCount(animals);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    // Task 13
    static Stream<Arguments> getArgumentsForGetAnimalsWithMoreThanTwoWordsNameTest() {
        return Stream.of(
            Arguments.of(List.of(), List.of()),
            Arguments.of(List.of(BIRD, DOG, FISH), List.of()),
            Arguments.of(List.of(DOG, SPLIT_NAME_SPIDER, SPIDER), List.of(SPLIT_NAME_SPIDER)),
            Arguments.of(List.of(SPLIT_NAME_SPIDER, SPLIT_NAME_SPIDER), List.of(SPLIT_NAME_SPIDER, SPLIT_NAME_SPIDER))
        );
    }

    @ParameterizedTest
    @DisplayName("Получение животных, чьё имя состоит более, чем из двух слов")
    @MethodSource("getArgumentsForGetAnimalsWithMoreThanTwoWordsNameTest")
    void testGetAnimalsWithMoreThanTwoWordsName(List<Animal> animals, List<Animal> expectedResult) {
        var actualResult = getAnimalsWithMoreThanTwoWordsName(animals);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    // Task 14
    static Stream<Arguments> getArgumentsForContainsDogBiggerThanKCentimetersTest() {
        return Stream.of(
            Arguments.of(List.of(), 1, false),
            Arguments.of(List.of(BIRD, DOG, FISH), 40, true),
            Arguments.of(List.of(DOG, LIGHT_BIRD, SPIDER), 50, false),
            Arguments.of(List.of(CAT, SPIDER, FISH), 0, false)
        );
    }

    @ParameterizedTest
    @DisplayName("Содержится ли в списке собака, которая выше К см")
    @MethodSource("getArgumentsForContainsDogBiggerThanKCentimetersTest")
    void testContainsDogBiggerThanKCentimeters(List<Animal> animals, int k, Boolean expectedResult) {
        var actualResult = containsDogBiggerThanKCentimeters(animals, k);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    // Task 15
    static Stream<Arguments> getArgumentsForGetSummaryWeightOfAnimalsWithAgeBetweenTest() {
        return Stream.of(
            Arguments.of(List.of(), 1, 2, 0),
            Arguments.of(List.of(BIRD, DOG, FISH), 1, 1, 0),
            Arguments.of(List.of(DOG, LIGHT_BIRD, SPIDER), 2, 5, 13030),
            Arguments.of(List.of(CAT, SPIDER, FISH), 1, 10, 6675)
        );
    }

    @ParameterizedTest
    @DisplayName("Получение суммарного веса животных возрастом между k и l")
    @MethodSource("getArgumentsForGetSummaryWeightOfAnimalsWithAgeBetweenTest")
    void testGetSummaryWeightOfAnimalsWithAgeBetween(List<Animal> animals, int k, int l, Integer expectedResult) {
        var actualResult = getSummaryWeightOfAnimalsWithAgeBetween(animals, k, l);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    // Task 16
    static Stream<Arguments> getArgumentsForSortByTypeThenSexThenNameTest() {
        return Stream.of(
            Arguments.of(List.of(), List.of()),
            Arguments.of(List.of(BIRD, CAT, FISH), List.of(CAT, BIRD, FISH)),
            Arguments.of(List.of(CAT, CAT, BIG_CAT), List.of(CAT, CAT, BIG_CAT)),
            Arguments.of(List.of(LIGHT_BIRD, CAT, BIRD), List.of(CAT, LIGHT_BIRD, BIRD))
        );
    }

    @ParameterizedTest
    @DisplayName("Сортировка по виду, затем по полу, затем по имени")
    @MethodSource("getArgumentsForSortByTypeThenSexThenNameTest")
    void testSortByTypeThenSexThenName(List<Animal> animals, List<Animal> expectedResult) {
        var actualResult = sortByTypeThenSexThenName(animals);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    // Task 17
    static Stream<Arguments> getArgumentsForCheckSpidersBiteOftenThanDogsTest() {
        return Stream.of(
            Arguments.of(List.of(), false),
            Arguments.of(List.of(BIRD, CAT, FISH), false),
            Arguments.of(List.of(SPIDER, SPLIT_NAME_SPIDER, DOG), true),
            Arguments.of(List.of(SPIDER, SPLIT_NAME_SPIDER, DOG2), false),
            Arguments.of(List.of(SPIDER, SPIDER, DOG2), true)
        );
    }

    @ParameterizedTest
    @DisplayName("Проверка, кусаются ли пауки чаще, чем собаки")
    @MethodSource("getArgumentsForCheckSpidersBiteOftenThanDogsTest")
    void testCheckSpidersBiteOftenThanDogs(List<Animal> animals, Boolean expectedResult) {
        var actualResult = checkSpidersBiteOftenThanDogs(animals);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    // Task 18
    static Stream<Arguments> getArgumentsForGetHeaviestFishInSeveralListsTest() {
        return Stream.of(
            Arguments.of(List.of(), null),
            Arguments.of(
                List.of(
                    List.of(BIRD, CAT, FISH),
                    List.of(LIGHT_FISH, CAT, HEAVY_FISH),
                    List.of(FISH, FISH, LIGHT_FISH)
                ),
                HEAVY_FISH
            ),
            Arguments.of(
                List.of(
                    List.of(DOG2, DOG, SPIDER),
                    List.of(LIGHT_FISH, CAT, SPIDER)
                ),
                LIGHT_FISH
            ),
            Arguments.of(
                List.of(
                    List.of(BIRD, FISH, FISH),
                    List.of(LIGHT_FISH, LIGHT_FISH),
                    List.of()
                ),
                FISH
            ),
            Arguments.of(
                List.of(
                    List.of(BIRD, DOG, CAT),
                    List.of(SPLIT_NAME_SPIDER, SPIDER)
                ),
                null
            )
        );
    }

    @ParameterizedTest
    @DisplayName("Получение самой тяжёлой рыбы в нескольких списках")
    @MethodSource("getArgumentsForGetHeaviestFishInSeveralListsTest")
    void testGetHeaviestFishInSeveralLists(List<List<Animal>> animalLists, Animal expectedResult) {
        var actualResult = getHeaviestFishInSeveralLists(animalLists);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    // Task 19
    static Stream<Arguments> getArgumentsForGetAnimalsNamesWithValidationErrorsTest() {
        return Stream.of(
            Arguments.of(List.of(), Map.of()),
            Arguments.of(
                List.of(
                    new Animal(null, Type.DOG, F, -5, 33, 22, false),
                    new Animal("Gretta", null, null, 11, 33, 22, true),
                    new Animal("Kevin", Type.BIRD, M, 11, -33, -22, true)
                ),
                Map.of(
                    "NoName", new LinkedHashSet<>(List.of(
                        new ValidationError("name", "Name is invalid"),
                        new ValidationError("age", "Age is invalid")
                    )),
                    "Gretta", new LinkedHashSet<>(List.of(
                        new ValidationError("type", "Type is invalid"),
                        new ValidationError("sex", "Sex is invalid")
                    )),
                    "Kevin", new LinkedHashSet<>(List.of(
                        new ValidationError("height", "Height is invalid"),
                        new ValidationError("weight", "Weight is invalid")
                    ))
                )
            ),
            Arguments.of(List.of(SPIDER, SPLIT_NAME_SPIDER, DOG), Map.of())
        );
    }

    @ParameterizedTest
    @DisplayName("Получение имен животных с невалидными полями и список ошибок")
    @MethodSource("getArgumentsForGetAnimalsNamesWithValidationErrorsTest")
    void testGetAnimalsNamesWithValidationErrors(
        List<Animal> animals,
        Map<String, Set<ValidationError>> expectedResult
    ) {
        var actualResult = getAnimalsNamesWithValidationErrors(animals);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    // Task 20
    static Stream<Arguments> getArgumentsForGetAnimalsNamesWithInvalidFieldNamesTest() {
        return Stream.of(
            Arguments.of(List.of(), Map.of()),
            Arguments.of(
                List.of(
                    new Animal(null, Type.DOG, F, -5, 33, 22, false),
                    new Animal("Gretta", null, null, 11, 33, 22, true),
                    new Animal("Kevin", Type.BIRD, M, 11, -33, -22, true)
                ),
                Map.of(
                    "NoName", "name, age",
                    "Gretta", "type, sex",
                    "Kevin", "height, weight"
                )
            ),
            Arguments.of(List.of(SPIDER, SPLIT_NAME_SPIDER, DOG), Map.of())
        );
    }

    @ParameterizedTest
    @DisplayName("Получение имен животных с названиями невалидных полей")
    @MethodSource("getArgumentsForGetAnimalsNamesWithInvalidFieldNamesTest")
    void testGetAnimalsNamesWithInvalidFieldNames(
        List<Animal> animals,
        Map<String, String> expectedResult
    ) {
        var actualResult = getAnimalsNamesWithInvalidFieldNames(animals);
        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
