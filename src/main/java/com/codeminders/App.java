package com.codeminders;

import com.codeminders.counter.DirectoryCodeLineCounter;
import com.codeminders.counter.JavaCodeLinesCounter;
import com.codeminders.counter.LinesCounter;
import com.codeminders.writer.ConsoleWriter;
import com.codeminders.writer.Writer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * @author Nazar Lelyak.
 */
public class App {

    private static LinesCounter getLineCounter(String name) {

        if (name == null || name.isEmpty() || !Files.exists(Paths.get(name))) {
            throw new IllegalArgumentException("Resource is incorrect: " + name);
        }

        Path path = Paths.get(name);
        if (Files.isDirectory(path)) {
            return new DirectoryCodeLineCounter(path);

        } else if (Files.isRegularFile(path) && name.toLowerCase().endsWith(".java")) {
            return new JavaCodeLinesCounter(path);
        }

        throw new IllegalArgumentException("Unknown name format is provided for processing: " + name);
    }

    /**
     * Main CLI method for processing input resource.
     *
     * @param args java file or resource location, like relative - `src/test/resources`
     *             or absolute `/home/nazar/Software/java/jdk1.8.0_45/src`.
     */
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
