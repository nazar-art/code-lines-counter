package com.codeminders.writer;

import com.codeminders.counter.LinesCounter;
import com.codeminders.model.LinesStats;

import java.io.PrintStream;
import java.util.Objects;

/**
 * @author Nazar Lelyak.
 */
public class ConsoleWriter implements Writer {

    private StringBuilder sb = new StringBuilder();

    @Override
    public void write(PrintStream out, LinesCounter linesCounter) {
        formatConsoleOutput("", linesCounter.countLines());
        out.print(sb.toString());
    }

    private void formatConsoleOutput(String tab, LinesStats report) {
        String prettyPrintMsg = String.format("%s%s : %s%n", tab, report.getResource().getFileName(), report.calculateTotalCodeLines());
        sb.append(prettyPrintMsg);

        if (report.getSubResources() != null) {
            report.getSubResources().stream()
                    .filter(Objects::nonNull)
                    .forEach(res -> formatConsoleOutput(tab.concat("  "), res));
        }
    }
}
