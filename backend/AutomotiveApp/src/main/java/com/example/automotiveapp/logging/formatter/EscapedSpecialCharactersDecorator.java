package com.example.automotiveapp.logging.formatter;

import com.example.automotiveapp.logging.log.LogRecord;
import org.apache.commons.lang3.StringEscapeUtils;

public class EscapedSpecialCharactersDecorator extends LogFormatterDecorator {

    public EscapedSpecialCharactersDecorator(LogFormatter delegate) {
        super(delegate);
    }

    @Override
    public String format(LogRecord log) {
        return StringEscapeUtils.escapeJava(super.format(log));
    }
}
