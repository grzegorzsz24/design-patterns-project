package com.example.automotiveapp.logging.formatter;

import com.example.automotiveapp.logging.log.LogRecord;

public class TextLogFormatter implements LogFormatter {

    @Override
    public String format(LogRecord log) {
        return "level: " +
                log.level().name() +
                ", " +
                "message: " +
                log.message();
    }
}
