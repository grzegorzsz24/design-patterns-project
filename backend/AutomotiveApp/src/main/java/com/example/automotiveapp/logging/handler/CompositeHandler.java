package com.example.automotiveapp.logging.handler;

import com.example.automotiveapp.logging.log.LogRecord;

import java.util.ArrayList;
import java.util.List;

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
}
