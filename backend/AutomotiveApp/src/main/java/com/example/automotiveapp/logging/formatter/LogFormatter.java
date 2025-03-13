package com.example.automotiveapp.logging.formatter;

import com.example.automotiveapp.logging.log.LogRecord;

// L3 Strategy - second impl
// L7 Functional interface - second impl
@FunctionalInterface
public interface LogFormatter {
    String format(LogRecord log);
}
