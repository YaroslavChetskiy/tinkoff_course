package edu.hw2.task4;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw2.task4.Task4.callingInfo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Task4Test {

    @Test
    @DisplayName("Тестирование callingInfo")
    void testCallingInfo() {
        CallingInfo callingInfo = callingInfo();

        assertAll(
            () -> assertThat(callingInfo.className()).isEqualTo(Task4Test.class.getName()),
            () -> assertThat(callingInfo.methodName()).isEqualTo("testCallingInfo")
        );
    }

}
