package edu.project4.util;

import edu.project4.exception.ImageUtilsException;
import edu.project4.model.FractalImage;
import edu.project4.model.Pixel;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static edu.project4.util.ImageFormat.BMP;
import static edu.project4.util.ImageFormat.JPEG;
import static edu.project4.util.ImageFormat.PNG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ImageUtilsTest {

    private static final Path TEST_PATH = Path.of("src", "test", "resources", "project4");

    private static final int IMAGE_SIZE = 100;
    public static final String FILE_NAME = "save_test";

    @ParameterizedTest
    @DisplayName("Сохранение изображения в нужном формате")
    @MethodSource("getArgumentsForSaveImageWithFormatTest")
    void saveImageWithFormat(ImageFormat format) throws IOException {
        Pixel[] data = new Pixel[IMAGE_SIZE * IMAGE_SIZE];
        Arrays.fill(data, new Pixel(255, 0, 0, 0));
        var image = new FractalImage(data, IMAGE_SIZE, IMAGE_SIZE);
        var fileName = TEST_PATH.resolve(FILE_NAME + format.getExtension());
        ImageUtils.save(image, fileName, format);
        assertThat(Files.exists(fileName)).isTrue();
        Files.delete(TEST_PATH.resolve(FILE_NAME + format.getExtension()));
    }

    static Stream<Arguments> getArgumentsForSaveImageWithFormatTest() {
        return Stream.of(
            Arguments.of(BMP),
            Arguments.of(JPEG),
            Arguments.of(PNG)
        );
    }

    @Test
    @DisplayName("Выброс исключения при неправильном пути к файлу")
    void throwImageUtilsExceptionIfFileNameIsInvalid() {
        var fileName = Path.of("dummy/not_existing");
        var exception = assertThrows(
            ImageUtilsException.class,
            () -> ImageUtils.save(FractalImage.create(IMAGE_SIZE, IMAGE_SIZE), fileName, PNG)
        );
        assertThat(exception.getCause()).isInstanceOf(IOException.class);
        assertThat(exception.getMessage()).isEqualTo("Could not save image in path: " + fileName);
    }
}
