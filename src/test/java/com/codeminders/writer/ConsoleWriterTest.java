package com.codeminders.writer;

import com.codeminders.counter.FileLinesCounter;
import com.codeminders.counter.LinesCounter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Nazar Lelyak.
 */
@Tag("Writer")
@DisplayName("Testing Console Writer")
class ConsoleWriterTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp(TestInfo testInfo, TestReporter reporter) {
        String infoMessage = String
                .format("Running: %s with Tags: %s%n", testInfo.getDisplayName(), testInfo.getTags());
        reporter.publishEntry(infoMessage);

        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testWriteToConsole() {
        LinesCounter linesCounter = new FileLinesCounter(
                Paths.get("src/test/resources/valid/3_code_lines.java")
        );
        new ConsoleWriter().write(System.out, linesCounter);

        String expected = String.format("3_code_lines.java : 3%s", System.lineSeparator());
        assertEquals(expected, outContent.toString());
    }
}