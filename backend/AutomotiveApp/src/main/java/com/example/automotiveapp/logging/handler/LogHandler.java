package com.example.automotiveapp.logging.handler;

import com.example.automotiveapp.logging.log.LogRecord;

// L3 Strategy - third impl
public interface LogHandler {
    void handle(LogRecord record);
}
