package com.codeminders.writer;

import com.codeminders.App;
import com.codeminders.BaseTest;
import com.codeminders.counter.FileLinesCounter;
import com.codeminders.counter.LinesCounter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

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

    private final StringBuilder expectedErrInfo = new StringBuilder();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        expectedErrInfo.append("Incorrect usage:").append(System.lineSeparator());
        expectedErrInfo.append("Please provide correct file or folder path").append(System.lineSeparator());
        expectedErrInfo.append("Example: /tmp/java_sources_folder").append(System.lineSeparator());
        expectedErrInfo.append("Example: /tmp/JavaDemo.java").append(System.lineSeparator());
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
        void testCorrectFileInput() {
            App.main(new String[]{"src/test/resources/valid/5_code_lines.java"});

            String expected = String.format("5_code_lines.java : 5%s", System.lineSeparator());
            assertEquals(expected, outContent.toString());
        }

        @Test
        void testCorrectFolderInput() {
            App.main(new String[]{"src/test/resources/valid"});

            StringBuilder sb = new StringBuilder();
            sb.append("valid : 8").append(System.lineSeparator());
            sb.append("  0_code_lines.java : 0").append(System.lineSeparator());
            sb.append("  3_code_lines.java : 3").append(System.lineSeparator());
            sb.append("  5_code_lines.java : 5").append(System.lineSeparator());

            assertEquals(sb.toString(), outContent.toString());
        }

        @Test
        void testInvalidFileInputs() {
            assertThrows(IllegalArgumentException.class, getAppInstance("file_does_not_exist"),
                    "if input is incorrect exception should be thrown");

            assertThrows(IllegalArgumentException.class, getAppInstance("src/test/resources/invalid/mat-photo.jpg"),
                    "if file is photo throw exception");

            assertThrows(IllegalArgumentException.class, getAppInstance("src/test/resources/invalid/test.txt"),
                    "if file is incorrect throw exception");

            assertThrows(IllegalArgumentException.class, getAppInstance("src/test/resources/invalid/test.json"),
                    "if file is json throw exception");
        }

        @Test
        void testInvalidFolderInputs() {
            assertThrows(IllegalArgumentException.class,
                    getAppInstance("src/test/resources/invalid"),
                    "if folder's content is invalid exception should be thrown");

            assertThrows(IllegalArgumentException.class,
                    getAppInstance("src/test/resources/folder_not_exist"),
                    "if folder doesn't exist exception should be thrown");
        }

        @Test
        void testIfEmptyInputExceptionShouldBeThrown() {
            App.main(new String[]{""});
            assertEquals(expectedErrInfo.toString(), errContent.toString());
        }

        @Test
        void testIfNoInputExceptionShouldBeThrown() {
            App.main(new String[]{});
            assertEquals(expectedErrInfo.toString(), errContent.toString());
        }

        @Test
        void testIfTwoInputsExceptionShouldBeThrown() {
            App.main(new String[]{"", ""});
            assertEquals(expectedErrInfo.toString(), errContent.toString());
        }

        private Executable getAppInstance(String... resources) {
            return () -> App.main(resources);
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