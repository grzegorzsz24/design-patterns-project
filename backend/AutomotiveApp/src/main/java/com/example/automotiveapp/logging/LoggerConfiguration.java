package com.example.automotiveapp.logging;

import com.example.automotiveapp.logging.dsl.Interpreter;
import com.example.automotiveapp.logging.filter.LogFilter;
import com.example.automotiveapp.logging.filter.LogLogFilter;
import com.example.automotiveapp.logging.filter.NegateFilterDecorator;
import com.example.automotiveapp.logging.filter.WarnLogFilter;
import com.example.automotiveapp.logging.formatter.*;
import com.example.automotiveapp.logging.handler.ConsoleLogHandler;
import com.example.automotiveapp.logging.handler.FileLogHandler;
import com.example.automotiveapp.logging.handler.LogHandler;
import com.example.automotiveapp.logging.log.LogLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Setter
@Component
@ConfigurationProperties("logger")
public class LoggerConfiguration {
    private List<Handler> handlers;
    private String dsl;

    @Getter
    @Setter
    public static class Handler {
        private String name;
        private String level;
        private String format;
        private String fileName;
    }

    public List<LogHandler> getLogHandlers() {
        if (handlers == null && dsl == null) {
            return new ArrayList<>();
        }

        if (handlers == null) {
            return Interpreter.interpret(dsl);
        }

        return handlers.stream().map(handler -> {
            List<LogFilter> logFilters = getLogFilters(handler.level);
            LogFormatter logFormatter = getLogFormatter(handler.format);
            return getLogHandler(handler.name, handler.fileName, logFormatter, logFilters);

        }).toList();
    }

    private LogHandler getLogHandler(String handler, String fileName, LogFormatter logFormatter, List<LogFilter> logFilters) {
        return switch (handler) {
            case "FILE" -> new FileLogHandler(logFormatter, logFilters, new File(fileName));
            case "CONSOLE" -> new ConsoleLogHandler(logFormatter, logFilters);
            // L7 Functional interface - third usage
            default -> log -> System.out.println(log.level().name() + ": " + log.message());
        };
    }


    private List<LogFilter> getLogFilters(String level) {
        return switch (level) {
            case "TRACE" -> List.of();
            case "LOG" -> List.of();
            case "WARN" -> List.of(new NegateFilterDecorator(new LogLogFilter()));
            case "ERROR" -> List.of(new LogLogFilter(), new WarnLogFilter());
            // L7 Functional interface - first usage
            default -> List.of(log -> log.level() != LogLevel.TRACE);
        };
    }

    private LogFormatter getLogFormatter(String format) {
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
}
