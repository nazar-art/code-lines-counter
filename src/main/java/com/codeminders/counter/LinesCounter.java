package com.codeminders.counter;

import com.codeminders.model.LinesStats;

/**
 * @author Nazar Lelyak.
 */
@FunctionalInterface
public interface LinesCounter {
    LinesStats countLines();
}
