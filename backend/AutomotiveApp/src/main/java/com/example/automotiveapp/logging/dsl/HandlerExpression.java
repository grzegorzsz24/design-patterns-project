package com.example.automotiveapp.logging.dsl;

import com.example.automotiveapp.logging.LogMapper;
import com.example.automotiveapp.logging.filter.LogFilter;
import com.example.automotiveapp.logging.formatter.LogFormatter;
import com.example.automotiveapp.logging.handler.LogHandler;

import java.util.List;

public class HandlerExpression implements Expression<LogHandler> {
    private final String name;
    private String filename;
    private final Expression<LogFormatter> formatter;
    private final Expression<List<LogFilter>> filters;

    public HandlerExpression(String name, String filename, FormatterExpression formatter, FiltersExpression filters) {
        this.name = name;
        this.filename = filename;
        this.formatter = formatter;
        this.filters = filters;
    }

    public HandlerExpression(String name, FormatterExpression formatter, FiltersExpression filters) {
        this.name = name;
        this.formatter = formatter;
        this.filters = filters;
    }

    @Override
    public LogHandler accept(Visitor visitor) {
        var formatter = this.formatter.accept(visitor);
        var filters = this.filters.accept(visitor);
        return visitor.visitHandler(new LogMapper.LogHandlerConfig(name, filename), formatter, filters);
    }

}
