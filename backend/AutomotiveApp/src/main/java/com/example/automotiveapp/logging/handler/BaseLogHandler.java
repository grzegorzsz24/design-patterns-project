package com.example.automotiveapp.logging.handler;

import com.example.automotiveapp.logging.filter.LogFilter;
import com.example.automotiveapp.logging.formatter.LogFormatter;
import com.example.automotiveapp.logging.log.LogRecord;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public abstract class BaseLogHandler implements LogHandler {

    private final LogFormatter formatter;
    private final List<LogFilter> filters;

    // L3 Template - first impl
    public void handle(LogRecord record) {
        for (LogFilter filter : filters) {
            if (!filter.isLoggable(record)) {
                return;
            }
        }
        String log = formatter.format(record);
        print(log);
    }

    protected abstract void print(String log);
}
