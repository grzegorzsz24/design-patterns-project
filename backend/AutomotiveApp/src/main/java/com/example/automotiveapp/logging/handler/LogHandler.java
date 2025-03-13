package com.example.automotiveapp.logging.handler;

import com.example.automotiveapp.logging.filter.LogFilter;
import com.example.automotiveapp.logging.formatter.LogFormatter;
import com.example.automotiveapp.logging.log.LogRecord;

import java.util.List;

// L3 Strategy - third impl
public interface LogHandler {
    void handle(LogRecord record);
    void setFormatter(LogFormatter formatter);
    void setFilters(List<LogFilter> filters);
}
