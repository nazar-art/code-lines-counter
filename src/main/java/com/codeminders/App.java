package com.codeminders;

import com.codeminders.counter.DirectoryCounter;
import com.codeminders.counter.FileCounter;
import com.codeminders.counter.LinesCounter;
import com.codeminders.writer.ConsoleLinesWriter;
import com.codeminders.writer.LinesResultWriter;

import java.io.File;

/**
 *
 */
public class App {
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

    private static LinesCounter getLineCounterProvider(String path) {
        File f = new File(path);

        if (f.isFile() && f.getName().toLowerCase().endsWith(".java")) {
            return new FileCounter(path);

        } else if (f.isDirectory()) {
            return new DirectoryCounter(path);
        }

        throw new IllegalArgumentException("Unknown path format is provided for processing: " + path);
    }
}
