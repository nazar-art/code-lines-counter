package com.codeminders.writer;

import com.codeminders.model.CountLinesResult;

import java.io.OutputStream;

/**
 * @author Nazar Lelyak.
 */
public interface LinesResultWriter {
    void write(OutputStream out, CountLinesResult result);
}
