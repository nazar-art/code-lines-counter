package com.codeminders.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nazar Lelyak.
 */
public class CountLinesResult {

    private String fileName;
    private int totalLinesCount;

    private List<CountLinesResult> children = new ArrayList<>();

    public CountLinesResult(String fileName) {
        this.fileName = fileName;
    }
}
