package com.codeminders.counter;

import com.codeminders.model.LinesStats;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * @author Nazar Lelyak.
 */
public class FileLinesCounter implements LinesCounter {

    private static final String BLOCK_CODE_START = "/*";
    private static final String BLOCK_CODE_END = "*/";
    private static final String LINE_CODE = "//";

    private static final String INLINE_BLOCK_COMMENT_REGEX = "/\\*/*(?s:(?!\\*/).)*\\*/";

    private Path filePath;

    public FileLinesCounter(Path path) {
        validateResource(path);
        validateFile(path);
        filePath = path;
    }

    private void validateFile(Path path) {
        if (!path.getFileName().toString().endsWith(".java")) {
            throw new IllegalArgumentException("Invalid java file: " + path.getFileName());
        }
    }

    @Override
    public LinesStats countLines() {
        return LinesStats.builder()
                .resource(filePath)
                .linesCount(countLinesForFile())
                .build();
    }

    int countLinesForFile() {
        int counter = 0;
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filePath.toFile())))) {
            boolean isBlockComment = false;

            while (scanner.hasNext()) {
                String line = scanner.nextLine().trim();

                if (line.startsWith(BLOCK_CODE_START)) {
                    isBlockComment = true;
                    continue;
                }

                // process block comments
                if (line.contains(BLOCK_CODE_START) && line.contains(BLOCK_CODE_END)) {
                    line = line.replaceAll(INLINE_BLOCK_COMMENT_REGEX, "");
                    isBlockComment = false;

                } else if (line.contains(BLOCK_CODE_END) && isBlockComment) {
                    line = line.replace(BLOCK_CODE_END, "");
                    isBlockComment = false;

                } else if (isBlockComment) {
                    continue;
                }

                // line comments
                if (line.contains(LINE_CODE)) {
                    line = line.substring(0, line.indexOf(LINE_CODE));
                }

                // count code results
                if (!line.isEmpty()) {
                    counter += 1;
                    //System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Exception during processing file: " + filePath);
        }
        return counter;
    }
}
