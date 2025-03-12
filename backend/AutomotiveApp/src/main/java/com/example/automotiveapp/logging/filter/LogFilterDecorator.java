package com.example.automotiveapp.logging.filter;

import com.example.automotiveapp.logging.log.LogRecord;

// L2 Decorator - second impl
public abstract class LogFilterDecorator implements LogFilter {
    private final LogFilter delegate;

    public LogFilterDecorator(LogFilter delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean isLoggable(LogRecord record) {
        return delegate.isLoggable(record);
    }
}
