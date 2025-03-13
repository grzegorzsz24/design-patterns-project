package com.example.automotiveapp.logging.dsl;

import com.example.automotiveapp.logging.handler.LogHandler;

public class HandlerExpression implements Expression<LogHandler> {
    private final String name;
    private String filename;
    private final FormatterExpression formatter;
    private final FiltersExpression filters;

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
        return visitor.visitHandler(name, filename, formatter, filters);
    }

}
