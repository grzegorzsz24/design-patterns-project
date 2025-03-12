package com.example.automotiveapp.logging;

import com.example.automotiveapp.logging.handler.CompositeHandler;
import com.example.automotiveapp.logging.handler.LogHandler;
import com.example.automotiveapp.logging.log.LogLevel;
import com.example.automotiveapp.logging.log.LogRecord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Logger {
    private final CompositeHandler handler = new CompositeHandler();

    public void subscribe(LogHandler handler) {
        this.handler.add(handler);
    }

    public void unsubscribe(LogHandler handler) {
        this.handler.remove(handler);
    }

    public void notify(LogRecord log) {
        handler.handle(log);
    }

    public void log(LogRecord log) {
        notify(log);
    }

    public void log(String message) {
        notify(new LogRecord(LogLevel.LOG, message));
    }

    public void warn(String message) {
        notify(new LogRecord(LogLevel.WARN, message));
    }

    public void error(String message) {
        notify(new LogRecord(LogLevel.ERROR, message));
    }
}
