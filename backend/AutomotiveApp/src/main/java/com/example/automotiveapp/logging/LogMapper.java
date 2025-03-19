package com.example.automotiveapp.logging;

import com.example.automotiveapp.logging.filter.LogFilter;
import com.example.automotiveapp.logging.filter.LogLogFilter;
import com.example.automotiveapp.logging.filter.NegateFilterDecorator;
import com.example.automotiveapp.logging.filter.WarnLogFilter;
import com.example.automotiveapp.logging.formatter.EscapedSpecialCharactersDecorator;
import com.example.automotiveapp.logging.formatter.JsonLogFormatter;
import com.example.automotiveapp.logging.formatter.LogFormatter;
import com.example.automotiveapp.logging.formatter.TextLogFormatter;
import com.example.automotiveapp.logging.formatter.XmlLogFormatter;
import com.example.automotiveapp.logging.handler.ConsoleLogHandler;
import com.example.automotiveapp.logging.handler.FileLogHandler;
import com.example.automotiveapp.logging.handler.LogHandler;
import com.example.automotiveapp.logging.log.LogLevel;

import java.io.File;
import java.util.List;

public class LogMapper {

    public static LogHandler getLogHandler(LogHandlerConfig config, LogFormatter logFormatter, List<LogFilter> logFilters) {
        return switch (config.name()) {
            case "FILE" -> new FileLogHandler(logFormatter, logFilters, new File(config.filename()));
            case "CONSOLE" -> new ConsoleLogHandler(logFormatter, logFilters);
            // L7 Functional interface - third usage
            default -> log -> System.out.println(log.level().name() + ": " + log.message());
        };
    }

    public static List<LogFilter> getLogFilters(String level) {
        return switch (level) {
            case "TRACE", "LOG" -> List.of();
            case "WARN" -> List.of(new NegateFilterDecorator(new LogLogFilter()));
            // L5 Liskov Substitution - third usage
            case "ERROR" -> List.of(new LogLogFilter(), new WarnLogFilter());
            // L7 Functional interface - first usage
            default -> List.of(log -> log.level() != LogLevel.TRACE);
        };
    }

    public static LogFormatter getLogFormatter(String format) {
        return switch (format) {
            case "ESCAPED_TEXT" -> new EscapedSpecialCharactersDecorator(new TextLogFormatter());
            case "ESCAPED_XML" -> new EscapedSpecialCharactersDecorator(new XmlLogFormatter());
            case "ESCAPED_JSON" -> new EscapedSpecialCharactersDecorator(new JsonLogFormatter());
            case "XML" -> new XmlLogFormatter();
            case "JSON" -> new JsonLogFormatter();
            // L7 Functional interface - second usage
            default -> log -> log.level().name() + ", " + log.message();
        };
    }

    public record LogHandlerConfig(String name, String filename) {
    }
}
