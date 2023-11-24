package edu.hw7.task3;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

class PersonDatabaseTest {

    private final static int NUMBER_OF_THREADS = 10;

    private final static int NUMBER_OF_OPERATIONS = 100;
    public static final Person TEST_PERSON = new Person(0, "Test Person", "Some address", "Some phone number");

    @ParameterizedTest
    @DisplayName("Тестирование основных операций в БД")
    @MethodSource("getArgumentsForDatabaseTests")
    void checkAllOperations(PersonDatabase personDatabase) {
        var byName = personDatabase.findByName(TEST_PERSON.name());
        var byAddress = personDatabase.findByAddress(TEST_PERSON.address());
        var byPhone = personDatabase.findByPhone(TEST_PERSON.phoneNumber());
        assertThat(byName).isEqualTo(byAddress).isEqualTo(byPhone).isEmpty();

        var persons = List.of(
            new Person(1, "Person1", "Address1", "PhoneNumber1"),
            new Person(2, "Person2", "Address2", "PhoneNumber2"),
            new Person(3, "Person3", "Address3", "PhoneNumber3")
        );

        for (Person person : persons) {
            personDatabase.add(person);
        }

        for (Person person : persons) {
            byName = personDatabase.findByName(person.name());
            byAddress = personDatabase.findByAddress(person.address());
            byPhone = personDatabase.findByPhone(person.phoneNumber());
            assertThat(byName).isEqualTo(byAddress).isEqualTo(byPhone).contains(person);
        }

        var person4_1 = new Person(4, "Person4", "Address4", "PhoneNumber4");
        var person4_2 = new Person(5, "Person4", "Address4", "PhoneNumber4");
        personDatabase.add(person4_1);
        personDatabase.add(person4_2);
        byName = personDatabase.findByName(person4_1.name());
        byAddress = personDatabase.findByAddress(person4_1.address());
        byPhone = personDatabase.findByPhone(person4_1.phoneNumber());
        assertThat(byName).isEqualTo(byAddress).isEqualTo(byPhone).containsAll(List.of(person4_1, person4_2));

        for (Person person : persons) {
            personDatabase.delete(person.id());
            byName = personDatabase.findByName(person.name());
            byAddress = personDatabase.findByAddress(person.address());
            byPhone = personDatabase.findByPhone(person.phoneNumber());
            assertThat(byName).isEqualTo(byAddress).isEqualTo(byPhone).isEmpty();
        }

    }


    @ParameterizedTest
    @DisplayName("Проверка многопоточности")
    @MethodSource("getArgumentsForDatabaseTests")
    void checkMultiThreadAccess(PersonDatabase personDatabase) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        CountDownLatch latch = new CountDownLatch(NUMBER_OF_THREADS);
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < NUMBER_OF_OPERATIONS; j++) {
                    switch (j % 2) {
                        case 0 -> {
                            personDatabase.add(TEST_PERSON);
                        }
                        case 1 -> {
                            personDatabase.delete(TEST_PERSON.id());
                        }
                    }
                }
                latch.countDown();
            });
        }
        latch.await();
        var byName = personDatabase.findByName(TEST_PERSON.name());
        var byAddress = personDatabase.findByAddress(TEST_PERSON.address());
        var byPhone = personDatabase.findByPhone(TEST_PERSON.phoneNumber());
        assertThat(byName).isEqualTo(byAddress).isEqualTo(byPhone).isEmpty();
    }

    static Stream<Arguments> getArgumentsForDatabaseTests() {
        return Stream.of(
            Arguments.of(new SynchronizedPersonDatabase()),
            Arguments.of(new ReadWriteLockedPersonDatabase())
        );
    }
}
