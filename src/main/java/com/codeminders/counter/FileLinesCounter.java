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

    public FileLinesCounter(Path resource) {
        validateResource(resource);
        validateFile(resource);
        this.filePath = resource;
    }

    private void validateFile(Path resource) {
        if (!resource.getFileName().toString().endsWith(".java")) {
            throw new IllegalArgumentException("Invalid java file: " + resource);
        }
    }

    @Override
    public LinesStats countLines() {
        return LinesStats.builder()
                .resource(filePath)
                .linesCount(countLinesForFile())
                .build();
    }

    public int countLinesForFile() {
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
                    line = processInlineBlockComment(line);
                    isBlockComment = false;

                } else if (line.contains(BLOCK_CODE_END) && isBlockComment) {
                    line = processBlockCodeEnd(line);
                    isBlockComment = false;

                } else if (isBlockComment) {
                    continue;
                }

                // line comments
                if (line.contains(LINE_CODE)) {
                    line = processLineComment(line);
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

    private String processBlockCodeEnd(String line) {
        return line.replace(BLOCK_CODE_END, "");
    }

    private String processInlineBlockComment(String line) {
        return line.replaceAll(INLINE_BLOCK_COMMENT_REGEX, "");
    }

    private String processLineComment(String line) {
        return line.substring(0, line.indexOf(LINE_CODE));
    }

}
