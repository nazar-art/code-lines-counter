package com.codeminders.writer;

import com.codeminders.counter.LinesCounter;
import com.codeminders.model.CountLinesReport;

import java.io.OutputStream;

/**
 * @author Nazar Lelyak.
 */
public class ConsoleLinesWriter implements LinesResultWriter {

    public ConsoleLinesWriter(LinesCounter fileParser) {

    }

    @Override
    public void write(OutputStream out, CountLinesReport result) {

    }
}
