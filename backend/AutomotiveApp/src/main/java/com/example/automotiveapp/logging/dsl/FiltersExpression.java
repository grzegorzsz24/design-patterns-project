package com.example.automotiveapp.logging.dsl;

import com.example.automotiveapp.logging.filter.LogFilter;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class FiltersExpression implements Expression<List<LogFilter>> {
    private final String level;

    @Override
    public List<LogFilter> accept(Visitor visitor) {
        return visitor.visitFilters(level);
    }
}

