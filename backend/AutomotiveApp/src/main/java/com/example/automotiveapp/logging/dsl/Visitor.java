package com.example.automotiveapp.logging.dsl;

import com.example.automotiveapp.logging.LogMapper;
import com.example.automotiveapp.logging.filter.LogFilter;
import com.example.automotiveapp.logging.formatter.LogFormatter;
import com.example.automotiveapp.logging.handler.LogHandler;

import java.util.List;

public class Visitor {

    public LogHandler visitHandler(LogMapper.LogHandlerConfig config, LogFormatter formatter, List<LogFilter> filters) {
        return LogMapper.getLogHandler(config, formatter, filters);
    }

    public LogFormatter visitFormatter(String format) {
        return LogMapper.getLogFormatter(format);
    }

    public List<LogFilter> visitFilters(String level) {
        return LogMapper.getLogFilters(level);
    }
}
