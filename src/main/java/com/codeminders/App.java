package com.codeminders;

import com.codeminders.counter.DirectoryCounter;
import com.codeminders.counter.FileCounter;
import com.codeminders.counter.LinesCounter;
import com.codeminders.writer.ConsoleLinesWriter;
import com.codeminders.writer.LinesResultWriter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 */
public class App {

    private static LinesCounter getLineCounterProvider(String name) {
        Path path = Paths.get(name);

        if (Files.isRegularFile(path) && name.toLowerCase().endsWith(".java")) {
            return new FileCounter(path);

        } else {
            if (Files.isDirectory(path)) {
                return new DirectoryCounter(path);
            }
        }

        throw new IllegalArgumentException("Unknown name format is provided for processing: " + name);
    }

    public static void main(String[] args) {
        if (args.length != 1 || args[0].isEmpty()) {
            System.err.println("Incorrect usage:");
            System.err.println("Please provide correct file or folder path");
            System.err.println("Example: /tmp/java_sources_folder");
            System.err.println("Example: /tmp/JavaDemo.java");
            System.exit(0);
        }

        String path = args[0];
        LinesCounter fileCounter = getLineCounterProvider(path);

        LinesResultWriter writer = new ConsoleLinesWriter(fileCounter);
        writer.write(System.out, fileCounter.countLines());
    }

}
