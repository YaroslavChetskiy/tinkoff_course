package edu.hw6.task6;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw6.task6.PortsChecker.Protocol;
import static edu.hw6.task6.PortsChecker.checkPortsAndMakeInfoFile;
import static edu.hw6.task6.PortsChecker.checkTcp;
import static edu.hw6.task6.PortsChecker.checkUdp;
import static org.assertj.core.api.Assertions.assertThat;

class PortsCheckerTest {

    private static final int MAX_PORT = 49151;

    @Test
    @DisplayName("Создание info файла по всем портам")
    void getPortsInfoFile() throws IOException {
        Path path = Path.of("src", "test", "resources", "hw6", "task6.txt");
        checkPortsAndMakeInfoFile(path, Protocol.TCP);
        assertThat(Files.exists(path)).isTrue();
        var lines = Files.lines(path);
        assertThat(lines).hasSize(MAX_PORT + 2);
    }

    @Test
    @DisplayName("Проверка статуса порта, если его занять")
    void getPortStatusIfItIsOccupied() throws IOException {
        var port = 8080;
        assertThat(checkTcp(port)).isFalse();
        try (var serverSocket = new ServerSocket(port)) {
            var actualResult = checkTcp(port);
            assertThat(actualResult).isTrue();
        }
        assertThat(checkUdp(port)).isFalse();
        try (var datagramSocket = new DatagramSocket(port)) {
            var actualResult = checkUdp(port);
            assertThat(actualResult).isTrue();
        }
    }

}
