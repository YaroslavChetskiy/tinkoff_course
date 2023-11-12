package edu.hw6.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Task2 {

    private static final String COPY_ENDING = " — копия";

    private Task2() {
    }

    public static void cloneFile(Path path) {

        var fileName = path.getFileName().toString();
        int extensionIndex = fileName.lastIndexOf('.');
        var fileNameWithoutExtension = extensionIndex > 0 ? fileName.substring(0, extensionIndex) : fileName;
        var extension = extensionIndex > 0 ? fileName.substring(extensionIndex) : "";

        int index = 1;
        Path destination;
        do {
            StringBuilder copyFileName = new StringBuilder(fileNameWithoutExtension + COPY_ENDING);
            if (index > 1) {
                copyFileName.append(" (").append(index).append(")");
            }
            copyFileName.append(extension);
            destination = path.getParent().resolve(copyFileName.toString());
            index++;
        } while (Files.exists(destination));

        try {
            Files.copy(path, destination);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
