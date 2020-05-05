package com.codeminders.writer;

import com.codeminders.counter.LinesCounter;
import com.codeminders.model.CountLinesResult;

import java.io.OutputStream;

/**
 * @author Nazar Lelyak.
 */
public class ConsoleLinesWriter implements LinesResultWriter {

    public ConsoleLinesWriter(LinesCounter fileParser) {

    }

    @Override
    public void write(OutputStream out, CountLinesResult result) {

    }
}
