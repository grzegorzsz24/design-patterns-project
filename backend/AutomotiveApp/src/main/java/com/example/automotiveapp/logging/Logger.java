package com.example.automotiveapp.logging;

import com.example.automotiveapp.logging.handler.CompositeHandler;
import com.example.automotiveapp.logging.handler.LogHandler;
import com.example.automotiveapp.logging.log.LogLevel;
import com.example.automotiveapp.logging.log.LogRecord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

// L3 Mediator - first impl
// L3 Observer - first impl
@Getter
@Setter
@AllArgsConstructor
public class Logger {
    private final CompositeHandler handler = new CompositeHandler();
    private final List<LogRecord> logs = new ArrayList<>();

    public void subscribe(LogHandler handler) {
        this.handler.add(handler);
    }

    public void unsubscribe(LogHandler handler) {
        this.handler.remove(handler);
    }

    public void notify(LogRecord log) {
        logs.add(log);
        handler.handle(log);
    }

    public void log(LogRecord log) {
        notify(log);
    }

    public void log(String message) {
        notify(new LogRecord(LogLevel.LOG, message));
    }

    public void trace(String message) {
        notify(new LogRecord(LogLevel.TRACE, message));
    }

    public void warn(String message) {
        notify(new LogRecord(LogLevel.WARN, message));
    }

    public void error(String message) {
        notify(new LogRecord(LogLevel.ERROR, message));
    }
}
