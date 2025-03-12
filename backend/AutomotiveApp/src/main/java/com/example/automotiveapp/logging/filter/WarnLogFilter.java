package com.example.automotiveapp.logging.filter;

import com.example.automotiveapp.logging.log.LogLevel;
import com.example.automotiveapp.logging.log.LogRecord;

public class WarnLogFilter implements LogFilter {

    @Override
    public boolean isLoggable(LogRecord record) {
        return record.level() == LogLevel.WARN;
    }
}
