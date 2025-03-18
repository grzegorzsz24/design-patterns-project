package com.example.automotiveapp.logging.filter;

import com.example.automotiveapp.logging.log.LogRecord;

// L3 Strategy - first impl
// L5 Liskov Substitution - third impl
// L5 Dependency Inversion - first impl
// L7 Functional interface - first impl
@FunctionalInterface
public interface LogFilter {
    boolean isLoggable(LogRecord record);
}
