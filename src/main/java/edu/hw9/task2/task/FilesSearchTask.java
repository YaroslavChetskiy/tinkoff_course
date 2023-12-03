package edu.hw9.task2.task;

import edu.hw9.task2.exception.FileSystemHandlerException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FilesSearchTask extends RecursiveTask<List<Path>> {

    private final Path dir;

    private final long size;

    private final String extension;

    public FilesSearchTask(Path dir, long size, String extension) {
        this.dir = dir;
        this.size = size;
        this.extension = extension;
    }

    @Override
    protected List<Path> compute() {
        try {
            List<Path> resultList = new ArrayList<>(Files.list(dir)
                .filter(path -> {
                        try {
                            return Files.isRegularFile(path)
                                && Files.size(path) == size
                                && path.getFileName().toString().endsWith(extension);
                        } catch (IOException e) {
                            throw new FileSystemHandlerException("Could not check file size: " + path, e);
                        }
                    }
                )
                .toList());

            Files.list(dir)
                .filter(Files::isDirectory)
                .map(dir -> new FilesSearchTask(dir, size, extension).fork())
                .forEach(subTask -> resultList.addAll(subTask.join()));

            return resultList;
        } catch (IOException e) {
            throw new FileSystemHandlerException("Could not handle dir: " + dir, e);
        }
    }
}
