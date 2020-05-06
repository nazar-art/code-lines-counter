package com.codeminders.counter;

import com.codeminders.model.CountLinesReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * @author Nazar Lelyak.
 */
public class FileCounter implements LinesCounter {

    private static final String BLOCK_CODE_START = "/*";
    private static final String BLOCK_CODE_END = "*/";
    private static final String LINE_CODE = "//";

    private static final String INLINE_BLOCK_COMMENT_REGEX = "/\\*/*(?s:(?!\\*/).)*\\*/";

    private final Path filePath;
    private CountLinesReport countLinesReport;

    public FileCounter(Path path) {
        this.filePath = path;
    }

    @Override
    public CountLinesReport countLines() {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filePath.toFile())))) {
            int counter = 0;
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
                    line = processLineComment(line);
                }

                // count results
                if (!line.isEmpty()) {
                    counter += 1;
                    System.out.println(line);
                }
            }
            countLinesReport = CountLinesReport.builder()
                    .filePath(filePath)
                    .totalLinesCount(counter)
                    .build();

        } catch (IOException e) {
            System.err.println("Exception during processing file: " + filePath);
        }

        return countLinesReport;
    }

    private String processLineComment(String line) {
        return line.substring(0, line.indexOf(LINE_CODE));
    }

    /*public static void main(String[] args) {
        String [] arr = {
                "src/test/resources/3_code_lines.java",
                "src/test/resources/5_code_lines.java"
        };

        for (String a : arr) {
            FileCounter fileCounter = new FileCounter(Paths.get(a));
            CountLinesReport report = fileCounter.countLines();
            System.out.println(report.getTotalLinesCount());
        }
    }*/
}
