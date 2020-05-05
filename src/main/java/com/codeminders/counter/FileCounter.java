package com.codeminders.counter;

import com.codeminders.model.CountLinesResult;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author Nazar Lelyak.
 */
public class FileCounter implements LinesCounter {

    private final String filePath;
    private CountLinesResult countLinesResult;

    private Pattern blockCommentRegex = Pattern.compile("/\\*/*(?s:(?!\\*/).)*\\*/");

    public FileCounter(String path) {
        this.filePath = path;
//        this.countLinesResult = new CountLinesResult(path);
    }

    @Override
    public CountLinesResult countLines() {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filePath)))) {
            int counter = 0;
            boolean isBlockComment = false;

            while (scanner.hasNext()) {
                String line = scanner.nextLine().trim();

                if (line.startsWith("/*")) {
                    isBlockComment = true;
                    continue;
                }

                if (line.contains("*/") && isBlockComment) {
                    line = line.substring(line.indexOf("*/"));

                    // remove all group comments for a line
                    line = line.replaceAll("/\\*/*(?s:(?!\\*/).)*\\*/", "");
                    isBlockComment = false;
                }

                if (line.contains("//") && !isBlockComment) {
                    line = line.substring(0, line.indexOf("//"));
                }

                if (!line.isEmpty() && !isBlockComment) {
                    counter += 1;
                }
            }
            countLinesResult = CountLinesResult.of(filePath, counter);
        } catch (IOException e) {
            System.err.println("Exception during processing file: " + filePath);
        }

        return countLinesResult;
    }

    public static void main(String[] args) {

        String[] array = {"/home/nazar/IdeaProjects/Companies/Codeminders/code-lines-counter-java/src/test/resources/3_code_lines.java", "/home/nazar/IdeaProjects/Companies/Codeminders/code-lines-counter-java/src/test/resources/5_code_lines.java"};

        for (String a : array) {
            FileCounter fileCounter = new FileCounter(a);
            CountLinesResult result = fileCounter.countLines();
            System.out.println(String.format("For file: %s - total count: %s%n", a, result.getTotalLinesCount()));
        }

    }
}
