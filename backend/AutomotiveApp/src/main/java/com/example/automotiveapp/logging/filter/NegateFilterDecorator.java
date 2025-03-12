package com.example.automotiveapp.logging.filter;

import com.example.automotiveapp.logging.log.LogRecord;

public class NegateFilterDecorator extends LogFilterDecorator {

    public NegateFilterDecorator(LogFilter delegate) {
        super(delegate);
    }

    @Override
    public boolean isLoggable(LogRecord record) {
        return !super.isLoggable(record);
    }
}
