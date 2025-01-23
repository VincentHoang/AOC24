package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class InputReaderToLines {
    private final List<String> lines;

    public InputReaderToLines(String filePath) throws IOException {
        this.lines = Files.readAllLines(Path.of(filePath));
    }

    public List<String> getLines() {
        return lines;
    }
}
