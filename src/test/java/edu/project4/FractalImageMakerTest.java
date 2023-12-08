package edu.project4;

import edu.project4.model.Rectangle;
import edu.project4.postProcessing.GammaCorrectionProcessor;
import edu.project4.renderer.MultiThreadRenderer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.project4.util.ImageFormat.PNG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FractalImageMakerTest {

    private static final Path TEST_PATH = Path.of("src", "test", "resources", "project4");
    public static final String FILE_NAME = "test_image";

    @Test
    @DisplayName("Создание изображения фрактального пламени без ошибок")
    void makeImage() throws IOException {
        var format = PNG;
        var fractalImageMaker = new FractalImageMaker(
            new MultiThreadRenderer(),
            List.of(point -> point),
            List.of(new GammaCorrectionProcessor()),
            new Rectangle(-5, -3, 12, 6),
            TEST_PATH,
            format
        );
        assertDoesNotThrow(() ->
            fractalImageMaker.makeImage(FILE_NAME, 100, 100, 100, 1, (short) 10, 1)
        );
        assertThat(Files.exists(TEST_PATH.resolve(FILE_NAME + format.getExtension()))).isTrue();
        Files.delete(TEST_PATH.resolve(FILE_NAME + format.getExtension()));
    }

    @Test
    @DisplayName("Выброс исключения при неправильной директории")
    void throwIllegalArgumentExceptionIfDirIsInvalid() {
        var dir = TEST_PATH.resolve("empty.txt");
        var exception = assertThrows(
            IllegalArgumentException.class,
            () -> new FractalImageMaker(
                null, null, null, null, dir, PNG
            )
        );
        assertThat(exception.getMessage()).isEqualTo("Path should be directory: " + dir);
    }
}
