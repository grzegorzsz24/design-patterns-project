package com.example.automotiveapp.logging.dsl;

import com.example.automotiveapp.logging.handler.LogHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Interpreter {
    public static List<LogHandler> interpret(String input) {
        var visitor = new Visitor();

        // L7 Stream - first usage
        return Arrays.stream(input.replaceAll("\\s+", "").split(";"))
                .map(keyValue -> Arrays.stream(keyValue.split(","))
                        .map(value -> value.split(":"))
                        .collect(Collectors.toMap(value -> value[0], value -> value[1])))
                .map(keyValues -> {
                    var formatter = new FormatterExpression(Optional.ofNullable(keyValues.get("format")).orElseThrow());
                    var filters = new FiltersExpression(Optional.ofNullable(keyValues.get("level")).orElseThrow());
                    return new HandlerExpression(keyValues.get("handler"), formatter, filters).accept(visitor);
                }).toList();
    }

}

