package com.example.automotiveapp.logging.handler;

import com.example.automotiveapp.logging.filter.LogFilter;
import com.example.automotiveapp.logging.formatter.LogFormatter;
import com.example.automotiveapp.logging.formatter.TextLogFormatter;
import com.example.automotiveapp.logging.log.LogRecord;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public abstract class BaseLogHandler implements LogHandler {

    private LogFormatter formatter;
    private List<LogFilter> filters;

    // L3 Template - first impl
    public void handle(LogRecord record) {
        for (LogFilter filter : filters) {
            if (!filter.isLoggable(record)) {
                return;
            }
        }
        String log = getFormatter().format(record);
        print(log);
    }

    protected abstract void print(String log);

    public LogFormatter getFormatter() {
        return formatter == null ? new TextLogFormatter() : formatter;
    }

    @Override
    public void setFormatter(LogFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public void setFilters(List<LogFilter> filters) {
        this.filters = filters;
    }
}
