package edu.project1.console;

import edu.project1.dictionary.Theme;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Scanner;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

class UserInputReaderTest {

    @ParameterizedTest
    @DisplayName("Получение вводимого пользователем значения")
    @MethodSource("getArgumentsForGetInputTest")
    void checkGetInput(Scanner scanner, String expectedResult) {
        UserInputReader userInputReader = new UserInputReader(scanner);
        var actualResult = userInputReader.getInput();
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForGetInputTest() {
        return Stream.of(
            Arguments.of(new Scanner("/quit"), "/quit"),
            Arguments.of(new Scanner(""), null),
            Arguments.of(new Scanner("1234"), "1234"),
            Arguments.of(new Scanner("hello"), "hello")
        );
    }

    @ParameterizedTest
    @DisplayName("Получение выбранной пользователем темы")
    @MethodSource("getArgumentsForGetCorrectThemeTest")
    void getCorrectTheme(Scanner scanner, Theme expectedResult) {
        UserInputReader userInputReader = new UserInputReader(scanner);
        var actualResult = userInputReader.chooseTheme();
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Stream<Arguments> getArgumentsForGetCorrectThemeTest() {
        return Stream.of(
            Arguments.of(new Scanner("0"), Theme.ANIMALS),
            Arguments.of(new Scanner("-1 1"), Theme.HOME),
            Arguments.of(new Scanner("fdjkfdjk dskdksd 2"), Theme.FOOD),
            Arguments.of(new Scanner("99 4 3"), Theme.JOBS)
        );
    }

}
