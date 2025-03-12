package com.example.automotiveapp.logging.formatter;

import com.example.automotiveapp.logging.log.LogRecord;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonLogFormatter implements LogFormatter {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String format(LogRecord log) {
        try {
            return mapper.writeValueAsString(log);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
