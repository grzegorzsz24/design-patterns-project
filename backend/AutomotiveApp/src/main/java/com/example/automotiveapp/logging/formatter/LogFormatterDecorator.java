package com.example.automotiveapp.logging.formatter;

import com.example.automotiveapp.logging.log.LogRecord;

// L2 Decorator - second impl
public abstract class LogFormatterDecorator implements LogFormatter {
    private final LogFormatter delegate;

    public LogFormatterDecorator(LogFormatter delegate) {
        this.delegate = delegate;
    }

    @Override
    public String format(LogRecord log) {
        return delegate.format(log);
    }

}
