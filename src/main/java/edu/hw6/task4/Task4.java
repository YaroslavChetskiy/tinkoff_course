package edu.hw6.task4;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;

public final class Task4 {

    private Task4() {
    }

    public static void writeToFileUsingOutputChain(String path, String output) {
        try (var outputStream = Files.newOutputStream(Path.of(path));
             var checkedOutputStream = new CheckedOutputStream(outputStream, new Adler32());
             var bufferedOutputStream = new BufferedOutputStream(checkedOutputStream);
             var outputStreamWriter = new OutputStreamWriter(bufferedOutputStream, StandardCharsets.UTF_8);
             var printWriter = new PrintWriter(outputStreamWriter)) {
            printWriter.write(output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
