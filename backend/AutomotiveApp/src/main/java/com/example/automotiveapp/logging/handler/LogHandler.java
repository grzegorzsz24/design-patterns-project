package com.example.automotiveapp.logging.handler;

import com.example.automotiveapp.logging.log.LogRecord;

// L3 Strategy - third impl
// L5 Liskov Substitution - second impl
// L5 Dependency Inversion - third impl
// L7 Functional interface - third impl
@FunctionalInterface
public interface LogHandler {
    void handle(LogRecord record);
}
