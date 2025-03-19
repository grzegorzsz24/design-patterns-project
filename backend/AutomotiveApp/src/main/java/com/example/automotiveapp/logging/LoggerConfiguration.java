package com.example.automotiveapp.logging;

import com.example.automotiveapp.logging.dsl.Interpreter;
import com.example.automotiveapp.logging.filter.LogFilter;
import com.example.automotiveapp.logging.formatter.LogFormatter;
import com.example.automotiveapp.logging.handler.LogHandler;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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
            // L5 Liskov Substitution - second usage
            // L5 Dependency Inversion - first usage
            List<LogFilter> logFilters = LogMapper.getLogFilters(handler.level);

            // L5 Dependency Inversion - second usage
            LogFormatter logFormatter = LogMapper.getLogFormatter(handler.format);

            LogMapper.LogHandlerConfig config = new LogMapper.LogHandlerConfig(handler.name, handler.fileName);
            // L5 Dependency Inversion - third usage
            return LogMapper.getLogHandler(config, logFormatter, logFilters);

        }).toList();
    }
}
