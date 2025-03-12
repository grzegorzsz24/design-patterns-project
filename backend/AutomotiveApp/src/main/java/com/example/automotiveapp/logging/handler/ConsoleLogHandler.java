package com.example.automotiveapp.logging.handler;

import com.example.automotiveapp.logging.filter.LogFilter;
import com.example.automotiveapp.logging.formatter.LogFormatter;

import java.util.List;

public class ConsoleLogHandler extends BaseLogHandler {

    public ConsoleLogHandler(LogFormatter formatter, List<LogFilter> filters) {
        super(formatter, filters);
    }

    @Override
    protected void print(String log) {
        System.out.println(log);
    }
}
