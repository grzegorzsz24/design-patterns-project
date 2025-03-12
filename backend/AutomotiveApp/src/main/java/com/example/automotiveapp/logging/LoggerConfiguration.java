package com.example.automotiveapp.logging;

import com.example.automotiveapp.logging.filter.LogFilter;
import com.example.automotiveapp.logging.filter.LogLogFilter;
import com.example.automotiveapp.logging.filter.NegateFilterDecorator;
import com.example.automotiveapp.logging.filter.WarnLogFilter;
import com.example.automotiveapp.logging.formatter.*;
import com.example.automotiveapp.logging.handler.ConsoleLogHandler;
import com.example.automotiveapp.logging.handler.FileLogHandler;
import com.example.automotiveapp.logging.handler.LogHandler;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Setter
@Component
@ConfigurationProperties("logger")
public class LoggerConfiguration {
    private List<Handler> handlers;

    @Getter
    @Setter
    public static class Handler {
        private String name;
        private String level;
        private String format;
        private String fileName;
    }

    public List<LogHandler> getLogHandlers() {
        return handlers.stream().map(handler -> {
            List<LogFilter> logFilters = getLogFilters(handler.level);
            LogFormatter logFormatter = getLogFormatter(handler.format);
            return (LogHandler) switch (handler.name) {
                case "FILE" -> new FileLogHandler(logFormatter, logFilters, new File(handler.fileName));
                case "CONSOLE" -> new ConsoleLogHandler(logFormatter, logFilters);
                default -> throw new IllegalStateException("Unexpected value: " + handler.name);
            };

        }).toList();
    }

    private List<LogFilter> getLogFilters(String level) {
        return switch (level) {
            case "LOG" -> List.of();
            case "WARN" -> List.of(new NegateFilterDecorator(new LogLogFilter()));
            case "ERROR" -> List.of(new LogLogFilter(), new WarnLogFilter());
            default -> throw new IllegalStateException("Unexpected value: " + level);
        };
    }

    private LogFormatter getLogFormatter(String format) {
        return switch (format) {
            case "ESCAPED_TEXT" -> new EscapedSpecialCharactersDecorator(new TextLogFormatter());
            case "ESCAPED_XML" -> new EscapedSpecialCharactersDecorator(new XmlLogFormatter());
            case "ESCAPED_JSON" -> new EscapedSpecialCharactersDecorator(new JsonLogFormatter());
            case "XML" -> new XmlLogFormatter();
            case "JSON" -> new JsonLogFormatter();
            default -> new TextLogFormatter();
        };
    }
}
