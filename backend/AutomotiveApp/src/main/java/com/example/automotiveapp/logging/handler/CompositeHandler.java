package com.example.automotiveapp.logging.handler;

import com.example.automotiveapp.logging.filter.LogFilter;
import com.example.automotiveapp.logging.formatter.LogFormatter;
import com.example.automotiveapp.logging.log.LogRecord;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class CompositeHandler implements LogHandler {

    private final List<LogHandler> handlers = new ArrayList<>();

    public void add(LogHandler handler) {
        handlers.add(handler);
    }

    public void remove(LogHandler handler) {
        handlers.remove(handler);
    }

    public CompositeHandler(BaseLogHandler... handlers) {
        this.handlers.addAll(List.of(handlers));
    }

    @Override
    public void handle(LogRecord record) {
        for (LogHandler handler : handlers) {
            handler.handle(record);
        }
    }

    @Override
    public void setFormatter(LogFormatter formatter) {
        for (LogHandler handler : handlers) {
            handler.setFormatter(formatter);
        }
    }

    @Override
    public void setFilters(List<LogFilter> filters) {
        for (LogHandler handler : handlers) {
            handler.setFilters(filters);
        }
    }
}
