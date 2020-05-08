package com.codeminders.console;

import com.codeminders.App;
import com.codeminders.BaseTest;
import com.codeminders.counter.FileLinesCounter;
import com.codeminders.counter.LinesCounter;
import com.codeminders.writer.ConsoleWriter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Nazar Lelyak.
 */
@DisplayName("Testing Console Output")
class ConsoleOutputTest implements BaseTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Nested
    @Tag("App")
    @DisplayName("Testing main method")
    class AppTest {

        @Test
        void testCorrectInput() {
            App.main(new String[]{"src/test/resources/valid/5_code_lines.java"});

            String expected = String.format("5_code_lines.java : 5%s", System.lineSeparator());
            assertEquals(expected, outContent.toString());
        }

        @Test
        void testEmptyInput() {
            StringBuilder expected = new StringBuilder("Incorrect usage:").append(System.lineSeparator());
            expected.append("Please provide correct file or folder path").append(System.lineSeparator());
            expected.append("Example: /tmp/java_sources_folder").append(System.lineSeparator());
            expected.append("Example: /tmp/JavaDemo.java").append(System.lineSeparator());

            App.main(new String[]{""});
            assertEquals(expected.toString(), errContent.toString());
        }

        @Test
        void testUnknownInput() {
            assertThrows(IllegalArgumentException.class, () -> App.main(new String[]{"unknown_input"}));
        }
    }

    @Nested
    @Tag("Writer")
    @DisplayName("Test Console Writer")
    class ConsoleWriterTest {

        @Test
        void testWriteToConsole() {
            LinesCounter linesCounter = new FileLinesCounter(
                    buildPath("src/test/resources/valid/3_code_lines.java")
            );
            new ConsoleWriter().write(System.out, linesCounter);

            String expected = String.format("3_code_lines.java : 3%s", System.lineSeparator());
            assertEquals(expected, outContent.toString());
        }
    }
}