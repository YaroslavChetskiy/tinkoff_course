package edu.hw9.task2.task;

import edu.hw9.task2.exception.FileSystemHandlerException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class DirectoriesSearchTask extends RecursiveTask<List<Path>> {
    private final Path dir;
    private final int filesCount;

    public DirectoriesSearchTask(Path dir, int filesCount) {
        this.dir = dir;
        this.filesCount = filesCount;
    }

    @Override
    protected List<Path> compute() {
        try {
            List<Path> resultList = new ArrayList<>();
            var filesCountInDir = Files.list(dir)
                .filter(Files::isRegularFile)
                .count();

            if (filesCountInDir > filesCount) {
                resultList.add(dir);
            }

            Files.list(dir)
                .filter(Files::isDirectory)
                .map(path -> new DirectoriesSearchTask(path, filesCount).fork())
                .forEach(subTask -> resultList.addAll(subTask.join()));

            return resultList;
        } catch (IOException e) {
            throw new FileSystemHandlerException("Could not handle dir: " + dir, e);
        }
    }
}
