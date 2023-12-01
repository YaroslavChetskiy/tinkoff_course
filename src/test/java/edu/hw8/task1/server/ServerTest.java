package edu.hw8.task1.server;

import edu.hw8.task1.client.QuoteClient;
import edu.hw8.task1.exception.ClientException;
import edu.hw8.task1.settings.ServerSettings;
import edu.hw8.task1.storage.QuoteStorage;
import java.io.IOException;
import java.time.Duration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ServerTest {

    private static final String TEST_KEY_WORD = "личности";
    private static final String TEST_QUOTE = "Не переходи на личности там, где их нет.";

    @Test
    @DisplayName("Корректное подключение клиентов к серверу, получение ответа от сервера")
    void getConnectionWithServerAndGetResponse() throws InterruptedException {
        var server = new QuoteServer(5, new QuoteStorage());
        var serverThread = new Thread(server::start);
        serverThread.start();

        Thread.sleep(Duration.ofSeconds(3L));

        var clientThread = new Thread(() -> {
            try (var client = new QuoteClient()) {
                client.start();
                client.send(TEST_KEY_WORD.getBytes());
                var response = client.waitResponse();
                assertThat(response).isPresent();
                var actualResult = new String(response.get());
                assertThat(actualResult).isEqualTo(TEST_QUOTE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        clientThread.start();
        clientThread.join();
        server.stop();
    }

    @Test
    @DisplayName("Выброс исключения при неправильном подключении")
    void throwClientExceptionIfConnectionIsRefused() throws InterruptedException {
        var server = new QuoteServer(5, new QuoteStorage(), new ServerSettings("localhost", 8080, 512));
        var serverThread = new Thread(server::start);
        serverThread.start();

        Thread.sleep(Duration.ofSeconds(3L));

        var clientThread = new Thread(() -> {
            try (var client = new QuoteClient(new ServerSettings("localhost", 5454, 0))) {
                Assertions.assertThrows(ClientException.class, client::start);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        clientThread.start();
        clientThread.join();
        server.stop();
    }
}
