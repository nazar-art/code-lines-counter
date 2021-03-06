package com.codeminders.writer;


import com.codeminders.counter.LinesCounter;

import java.io.PrintStream;

/**
 * @author Nazar Lelyak.
 */
@FunctionalInterface
public interface Writer {
    void write(PrintStream out, LinesCounter linesCounter);
}
