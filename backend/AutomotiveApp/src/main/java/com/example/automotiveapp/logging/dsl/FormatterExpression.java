package com.example.automotiveapp.logging.dsl;

import com.example.automotiveapp.logging.formatter.LogFormatter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FormatterExpression implements Expression<LogFormatter> {
    private final String format;

    @Override
    public LogFormatter accept(Visitor visitor) {
        return visitor.visitFormatter(format);
    }
}

