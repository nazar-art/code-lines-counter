package com.codeminders;

import com.codeminders.counter.JavaCodeLinesCounter;
import com.codeminders.counter.LinesCounter;
import com.codeminders.writer.ConsoleWriter;
import com.codeminders.writer.Writer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 */
public class App {

    private static LinesCounter getLineCounter(String name) {
        Path path = Paths.get(name);

        if (Files.exists(path)) {
            return new JavaCodeLinesCounter(path);
        }

        throw new IllegalArgumentException("Illegal resource format is provided: " + name);
    }

    public static void main(String[] args) {
        if (args.length != 1 || args[0].isEmpty()) {
            System.err.println("Incorrect usage:");
            System.err.println("Please provide correct file or folder path");
            System.err.println("Example: /tmp/java_sources_folder");
            System.err.println("Example: /tmp/JavaDemo.java");
            System.exit(0);
        }

        String resource = args[0];
        LinesCounter fileCounter = getLineCounter(resource);

        Writer writer = new ConsoleWriter();
        writer.write(System.out, fileCounter);
    }

}
