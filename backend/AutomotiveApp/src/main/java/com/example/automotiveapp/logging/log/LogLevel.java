package com.example.automotiveapp.logging.log;

public record LogLevel(String name) {
    public static final LogLevel TRACE = new LogLevel("TRACE");
    public static final LogLevel LOG = new LogLevel("LOG");
    public static final LogLevel WARN = new LogLevel("WARN");
    public static final LogLevel ERROR = new LogLevel("ERROR");
}
