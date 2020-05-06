package com.codeminders.writer;

import com.codeminders.model.CountLinesReport;

import java.io.OutputStream;

/**
 * @author Nazar Lelyak.
 */
public interface LinesResultWriter {
    void write(OutputStream out, CountLinesReport result);
}
