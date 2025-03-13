package com.example.automotiveapp.logging.dsl;

import com.example.automotiveapp.logging.filter.LogFilter;
import com.example.automotiveapp.logging.filter.LogLogFilter;
import com.example.automotiveapp.logging.filter.NegateFilterDecorator;
import com.example.automotiveapp.logging.filter.WarnLogFilter;
import com.example.automotiveapp.logging.formatter.*;
import com.example.automotiveapp.logging.handler.ConsoleLogHandler;
import com.example.automotiveapp.logging.handler.FileLogHandler;
import com.example.automotiveapp.logging.handler.LogHandler;

import java.io.File;
import java.util.List;

public class Visitor {

    public LogHandler visitHandler(String name, String filename, LogFormatter formatter, List<LogFilter> filters) {
        return switch (name) {
            case "FILE" -> new FileLogHandler(formatter, filters, new File(filename));
            case "CONSOLE" -> new ConsoleLogHandler(formatter, filters);
            default -> throw new IllegalStateException("Unexpected value: " + name);
        };
    }

    public LogFormatter visitFormatter(String format) {
        return switch (format) {
            case "ESCAPED_TEXT" -> new EscapedSpecialCharactersDecorator(new TextLogFormatter());
            case "ESCAPED_XML" -> new EscapedSpecialCharactersDecorator(new XmlLogFormatter());
            case "ESCAPED_JSON" -> new EscapedSpecialCharactersDecorator(new JsonLogFormatter());
            case "XML" -> new XmlLogFormatter();
            case "JSON" -> new JsonLogFormatter();
            default -> new TextLogFormatter();
        };
    }

    public List<LogFilter> visitFilters(String level) {
        return switch (level) {
            case "LOG" -> List.of();
            case "WARN" -> List.of(new NegateFilterDecorator(new LogLogFilter()));
            case "ERROR" -> List.of(new LogLogFilter(), new WarnLogFilter());
            default -> throw new IllegalStateException("Unexpected value: " + level);
        };
    }
}
